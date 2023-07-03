package es.caib.sistramit.core.service.test;

import org.apache.commons.lang3.StringUtils;

public class TestSwift {

	public static void main(final String[] args) throws Exception {
		final String bic = "BMARES2MXXX";
		final boolean swift = esNumeroSwiftValido(bic);
		System.out.println("Swift: " + esNumeroSwiftValido(bic));
	}

	public static boolean esNumeroSwiftValido(final String codigo) {
		boolean valido;
		if (codigo == null || (codigo.length() != 8 && codigo.length() != 11)) {
			valido = false;
		} else {
			final String banco = codigo.substring(0, 4);
			final String pais = codigo.substring(4, 6);
			final String localidad = codigo.substring(6, 8);
			String sucursal = null;
			if (codigo.length() == 11) {
				sucursal = codigo.substring(8, 11);
			}
			valido = esNumeroSwiftValido(banco, pais, localidad, sucursal);
		}
		return valido;
	}

	public static boolean esNumeroSwiftValido(final String banco, final String pais, final String localidad,
			final String sucursal) {
		final boolean codigoBancoCorrecto = banco != null && banco.length() == 4 && StringUtils.isAllUpperCase(banco);
		final boolean codigoPaisCorrecto = pais != null && pais.length() == 2 && StringUtils.isAllUpperCase(pais);
		final boolean codigoLocalidadCorrecto = localidad != null && localidad.length() == 2
				&& (StringUtils.isNumeric(localidad) || StringUtils.isAllUpperCase(localidad)
						|| (StringUtils.isAlphanumeric(localidad) && localidad.matches("^[A-Z0-9]{2,}$")));
		final boolean codigoSucursalCorrecto = esNumeroSwiftValidoSucursal(sucursal);

		return codigoBancoCorrecto && codigoPaisCorrecto && codigoLocalidadCorrecto && codigoSucursalCorrecto;
	}

	private static boolean esNumeroSwiftValidoSucursal(final String sucursal) {
		boolean validoSucursal;
		if (sucursal == null) {
			validoSucursal = true;
		} else {
			validoSucursal = true;
			for (final char caracter : sucursal.toCharArray()) {
				if (!StringUtils.isAllUpperCase(String.valueOf(caracter))
						&& !StringUtils.isNumeric(String.valueOf(caracter))) {
					validoSucursal = false;
					break;
				}
			}
		}
		return validoSucursal;
	}

}
