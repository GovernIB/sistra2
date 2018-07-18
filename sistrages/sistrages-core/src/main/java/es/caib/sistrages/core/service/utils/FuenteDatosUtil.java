package es.caib.sistrages.core.service.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import es.caib.sistrages.core.api.exception.FuenteDatosConsultaDominioPeticionIncorrecta;
import es.caib.sistrages.core.api.model.ValorParametroDominio;
import es.caib.sistrages.core.api.model.ValoresDominio;
import es.caib.sistrages.core.service.model.ConsultaFuenteDatos;
import es.caib.sistrages.core.service.model.FiltroConsultaFuenteDatos;
import es.caib.sistrages.core.service.repository.model.JFilasFuenteDatos;
import es.caib.sistrages.core.service.repository.model.JValorFuenteDatos;

public class FuenteDatosUtil {

    public static String getValor(JFilasFuenteDatos f, String idCampo) {
        String valor = null;
        if (idCampo != null && f != null && f.getValoresFuenteDatos() != null
                && f.getValoresFuenteDatos().size() > 0) {
            for (final Iterator<JValorFuenteDatos> it = f
                    .getValoresFuenteDatos().iterator(); it.hasNext();) {
                final JValorFuenteDatos vfd = it.next();
                if (vfd.getCampoFuenteDatos() != null
                        && idCampo.equalsIgnoreCase(
                                vfd.getCampoFuenteDatos().getIdCampo())) {
                    valor = vfd.getValor();
                    break;
                }
            }
        }
        return valor;
    }

    public static ConsultaFuenteDatos decodificarConsulta(
            String consultaFuenteDatos) {

        // Estructura
        // SELECT campo (, campo)*
        // FROM fuenteDatos
        // WHERE expresion ( [AND | OR] expresion ] )* expresion: campo [= |
        // LIKE] :parametro
        // ORDER BY campo

        final ConsultaFuenteDatos cfd = new ConsultaFuenteDatos();

        if (StringUtils.isBlank(consultaFuenteDatos)) {
            throw new FuenteDatosConsultaDominioPeticionIncorrecta(
                    "Estructura consulta no es valida: consulta vacia");
        }

        final int indexFrom = StringUtils.indexOfIgnoreCase(consultaFuenteDatos,
                "FROM ");
        final int indexWhere = StringUtils
                .indexOfIgnoreCase(consultaFuenteDatos, "WHERE ");
        final int indexOrderby = StringUtils
                .indexOfIgnoreCase(consultaFuenteDatos, "ORDER BY ");

        // SELECT campo (, campo)*
        consultaFuenteDatos = consultaFuenteDatos.trim();
        if (!StringUtils.startsWithIgnoreCase(consultaFuenteDatos, "SELECT ")) {
            throw new FuenteDatosConsultaDominioPeticionIncorrecta(
                    "Estructura consulta no es valida: no empieza con SELECT");
        }

        if (indexFrom == -1) {
            throw new FuenteDatosConsultaDominioPeticionIncorrecta(
                    "Estructura consulta no es valida: no se encuentra FROM");
        }
        String select = consultaFuenteDatos.substring("SELECT ".length(),
                indexFrom);
        select = select.trim();
        final String campos[] = select.split(",");
        if (campos == null || campos.length == 0) {
            throw new FuenteDatosConsultaDominioPeticionIncorrecta(
                    "Estructura consulta no es valida: no se encuentran campos en SELECT");
        }
        for (int i = 0; i < campos.length; i++) {
            cfd.getCampos().add(campos[i].trim());
        }

        // FROM fuenteDatos
        String from;
        if (indexWhere != -1) {
            from = consultaFuenteDatos.substring(indexFrom + "FROM ".length(),
                    indexWhere);
        } else if (indexOrderby != -1) {
            from = consultaFuenteDatos.substring(indexFrom + "FROM ".length(),
                    indexOrderby);
        } else {
            from = consultaFuenteDatos.substring(indexFrom + "FROM ".length());
        }
        from = from.trim();
        cfd.setIdFuenteDatos(from);

        // WHERE expresion ( [AND | OR] expresion ] )* expresion: campo [= |
        // LIKE] :parametro
        if (indexWhere != -1) {
            String where;
            if (indexOrderby != -1) {
                where = consultaFuenteDatos.substring(
                        indexWhere + "WHERE ".length(), indexOrderby);
            } else {
                where = consultaFuenteDatos
                        .substring(indexWhere + "WHERE ".length());
            }

            final List<String> expresiones = getExpresionesWhere(where);
            final List<String> parametrosExpresiones = getParametrosExpresiones(
                    where);

            if (expresiones.size() != parametrosExpresiones.size()) {
                throw new FuenteDatosConsultaDominioPeticionIncorrecta(
                        "No coincide numero de expresiones con numero de parametros");
            }

            int i = 0;
            for (String exp : expresiones) {

                final FiltroConsultaFuenteDatos ffd = new FiltroConsultaFuenteDatos();

                // Conector
                if (StringUtils.startsWithIgnoreCase(exp, "AND ")) {
                    ffd.setConector(FiltroConsultaFuenteDatos.AND);
                    exp = exp.substring("AND ".length());
                } else if (StringUtils.startsWithIgnoreCase(exp, "OR ")) {
                    ffd.setConector(FiltroConsultaFuenteDatos.OR);
                    exp = exp.substring("OR ".length());
                }

                // Operador
                if (StringUtils.endsWithIgnoreCase(exp, "=")) {
                    ffd.setOperador(FiltroConsultaFuenteDatos.IGUAL);
                } else if (StringUtils.endsWithIgnoreCase(exp, " LIKE")) {
                    ffd.setOperador(FiltroConsultaFuenteDatos.LIKE);
                } else {
                    throw new FuenteDatosConsultaDominioPeticionIncorrecta(
                            "Estructura consulta no es valida: operador no reconocido");
                }

                // Campo
                final int indiceIgual = exp.indexOf("=");
                final int indiceLike = StringUtils.indexOfIgnoreCase(exp,
                        " LIKE");

                exp = exp.substring(0, Math.max(indiceIgual, indiceLike))
                        .trim();
                ffd.setCampo(exp);

                // Parametro
                ffd.setParametro(parametrosExpresiones.get(i));

                cfd.getFiltros().add(ffd);

                i++;
            }
        }

        // ORDER BY
        if (indexOrderby != -1) {
            final String orderBy = consultaFuenteDatos
                    .substring(indexOrderby + "ORDER BY ".length());
            cfd.setCampoOrden(orderBy.trim());
        }

        return cfd;
    }

    private static List<String> getExpresionesWhere(String where) {
        final String split[] = where.split(":");
        final List<String> expresiones = new ArrayList<>();
        boolean primer = true;
        for (final String s : split) {
            String exp = null;

            if (primer) {
                exp = s;
            } else {
                int next;
                next = s.indexOf(" AND");
                if (next == -1) {
                    next = s.indexOf(" OR");
                }
                if (next != -1) {
                    exp = s.substring(next);
                }
            }
            primer = false;

            if (StringUtils.isNotBlank(exp)) {
                expresiones.add(exp.trim());
            }
        }
        return expresiones;
    }

    private static List<String> getParametrosExpresiones(final String where) {
        final List<String> parametros = new ArrayList<>();
        final Pattern MY_PATTERN = Pattern.compile(":[a-zA-Z0-9_-]*");
        final Matcher m = MY_PATTERN.matcher(where);
        while (m.find()) {
            final String s = m.group();
            parametros.add(s.substring(1));
        }
        return parametros;
    }

    public static void ordenarFilas(List filas, String campoOrden) {
        if (StringUtils.isNotBlank(campoOrden) && filas != null
                && filas.size() > 0) {
            Collections.sort(filas, new FilaFuenteDatosComparator(campoOrden));
        }
    }

    public static ValoresDominio generarValores(List filas, List campos) {

        final ValoresDominio vfd = new ValoresDominio();

        if (filas != null && filas.size() > 0) {
            for (final Iterator it = filas.iterator(); it.hasNext();) {
                final JFilasFuenteDatos ffd = (JFilasFuenteDatos) it.next();
                final int numFila = vfd.addFila();
                for (final Iterator it2 = campos.iterator(); it2.hasNext();) {
                    final String idCampo = (String) it2.next();
                    final String valorCampo = getValor(ffd, idCampo);
                    vfd.setValor(numFila, idCampo, valorCampo);
                }
            }
        }

        return vfd;
    }

    public static String getValorParametro(String parametro,
            List<ValorParametroDominio> valorParametros) {
        String valor = null;
        if (valorParametros != null) {
            for (final ValorParametroDominio vp : valorParametros) {
                if (vp.getCodigo().equalsIgnoreCase(parametro)) {
                    valor = vp.getValor();
                    break;
                }
            }
        }
        return valor;
    }

}
