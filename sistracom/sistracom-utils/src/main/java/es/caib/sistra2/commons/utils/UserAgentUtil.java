package es.caib.sistra2.commons.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * Utilidades User Agent.
 */
public final class UserAgentUtil {

	private UserAgentUtil() {
	}

	/**
	 * S.O. BLACKBERRY.
	 */
	private static final String SO_BLACKBERRY = "BB";

	/**
	 * S.O. SONY ERICSSON.
	 */
	private static final String SO_SONYERICSSON = "SE";

	/**
	 * S.O. SYMBIAN.
	 */
	private static final String SO_SYMBIAN = "SY";

	/**
	 * S.O. IPOD.
	 */
	private static final String SO_IPOD = "IPOD";

	/**
	 * S.O. IPAD.
	 */
	private static final String SO_IPAD = "IPAD";

	/**
	 * S.O. PALM.
	 */
	private static final String SO_PALM = "PALM";

	/**
	 * S.O. WEB OS.
	 */
	private static final String SO_WEBOS = "WEBOS";

	/**
	 * S.O. IPHONE.
	 */
	private static final String SO_IPHONE = "IPHONE";

	/**
	 * S.O. WINDOWS.
	 */
	private static final String SO_WINDOWS = "WIN";

	/**
	 * S.O. MAC.
	 */
	private static final String SO_MAC = "MAC";

	/**
	 * S.O. LINUX.
	 */
	private static final String SO_LINUX = "LINUX";

	/**
	 * S.O. ANDROID.
	 */
	public static final String SO_ANDROID = "ANDROID";

	/** Atributo constante N1 de */
	public static final int N1 = 1;

	/** Atributo constante N2 de */
	public static final int N2 = 2;

	/** Atributo constante N3 de */
	public static final int N3 = 3;

	/** Atributo constante N4 de */
	public static final int N4 = 4;

	/** Atributo constante N5 de */
	public static final int N5 = 5;

	/** Atributo constante N6 de */
	public static final int N6 = 6;

	/** Atributo constante N7 de */
	public static final int N7 = 7;

	/** Atributo constante N8 de */
	public static final int N8 = 8;

	/** Atributo constante N9 de */
	public static final int N9 = 9;

	/** Atributo constante N10 de */
	public static final int N10 = 10;

	/** Atributo constante N23 de */
	public static final int N23 = 23;

	/** Atributo constante NUMERO_UNO de */
	public static final int NUMERO_UNO = 1;

	/** Atributo constante NUMERO_MENOS_UNO de */
	public static final int NUMERO_MENOS_UNO = -1;

	/** Atributo constante OTROS de */
	public static final String OTROS = "O";

	/** Atributo constante MSIE de */
	public static final String MSIE = "msie";

	/** Atributo constante WEBTV de */
	public static final String WEBTV = "webtv";

	/** Atributo constante SAFARI de */
	public static final String SAFARI = "safari";

	/** Atributo constante NETSCAPE de */
	public static final String NETSCAPE = "netscape";

	/** Atributo constante OPERA de */
	public static final String OPERA = "opera";

	public static final String KONQUEROR = "konqueror";

	public static final String IEMOBILE = "iemobile";

	public static final String SILK = "silk";

	public static final String DOLPHIN = "dolfin";

	/**
	 * Atributo constante MOZILLA de
	 */
	public static final String MOZILLA = "mozilla";

	/**
	 * Atributo constante CHROME de
	 */
	public static final String CHROME = "chrome";

	/**
	 * Serializa user agent en una cadena que contiene pares propiedad/valor.
	 *
	 * @param pUserAgent
	 *            UserAgent
	 * @return cadena que contiene pares propiedad/valor
	 */
	public static String serializeUserAgent(final String pUserAgent) {
		final String navegador = getNavegador(pUserAgent);
		final String versionNavegador = getVersionNavegador(pUserAgent);
		final String sistemaOperativo = getSistemaOperativo(pUserAgent);
		final String versionSistemaOperativo = getVersionSistemaOperativo(sistemaOperativo, pUserAgent);

		final Map<String, String> props = new HashMap<String, String>();
		props.put("navegador", navegador);
		props.put("versionNavegador", versionNavegador);
		props.put("sistemaOperativo", sistemaOperativo);
		props.put("versionSistemaOperativo", versionSistemaOperativo);

		final String userAgentSerialized = SerializadorMap.serializar(props);

		return userAgentSerialized;
	}

	/**
	 * Deserializa user agent en un map de propiedades.
	 *
	 * @param pUserAgentSerialized
	 *            Parámetro user agent serialized
	 * @return cadena que contiene pares propiedad/valor (SerializadorMap)
	 */
	public static Map<String, String> deserializeUserAgent(final String pUserAgentSerialized) {
		return SerializadorMap.deserializar(pUserAgentSerialized);
	}

	/**
	 * Obtiene el atributo navegador de UserAgent.
	 *
	 * @param pUserAgent
	 *            Parámetro user agent
	 * @return el atributo navegador
	 */
	public static String getNavegador(final Map<String, String> pUserAgent) {
		return pUserAgent.get("navegador");
	}

	/**
	 * Obtiene el atributo version navegador de UserAgent.
	 *
	 * @param pUserAgent
	 *            Parámetro user agent
	 * @return el atributo version navegador
	 */
	public static String getVersionNavegador(final Map<String, String> pUserAgent) {
		return pUserAgent.get("versionNavegador");
	}

	/**
	 * Obtiene el atributo sistema operativo de UserAgent.
	 *
	 * @param pUserAgent
	 *            Parámetro user agent
	 * @return el atributo sistema operativo
	 */
	public static String getSistemaOperativo(final Map<String, String> pUserAgent) {
		return pUserAgent.get("sistemaOperativo");
	}

	/**
	 * Obtiene el atributo version sistema operativo de UserAgent.
	 *
	 * @param pUserAgent
	 *            Parámetro user agent
	 * @return el atributo sistema operativo
	 */
	public static String getVersionSistemaOperativo(final Map<String, String> pUserAgent) {
		return pUserAgent.get("versionSistemaOperativo");
	}

	/**
	 * Obtiene el atributo navegador de UserAgent.
	 *
	 * @param pUserAgent
	 *            Parámetro user agent
	 * @return el atributo navegador
	 */
	public static String getNavegador(final String pUserAgent) {
		String pu = "";
		String resultado = null;
		try {
			if (pu != null) {
				pu = pUserAgent;
			}
			final String userAgent = pu.toLowerCase();
			// Detectamos navegador
			if ((userAgent.indexOf(NETSCAPE) != NUMERO_MENOS_UNO)
					|| (userAgent.indexOf("navigator") != NUMERO_MENOS_UNO)
					|| (userAgent.indexOf("ns8") != NUMERO_MENOS_UNO)) {
				resultado = "NS";
			} else if (userAgent.indexOf(CHROME) > NUMERO_MENOS_UNO) {
				resultado = "CH";
			} else if ((userAgent.indexOf(MSIE) != NUMERO_MENOS_UNO) && (userAgent.indexOf(OPERA) == NUMERO_MENOS_UNO)
					&& (userAgent.indexOf(WEBTV) == NUMERO_MENOS_UNO)
					&& (userAgent.indexOf(IEMOBILE) == NUMERO_MENOS_UNO)) {
				resultado = "IE";
			} else if (userAgent.indexOf(OPERA) != NUMERO_MENOS_UNO) {
				resultado = "OP";
			} else if (userAgent.indexOf(KONQUEROR) != NUMERO_MENOS_UNO) {
				resultado = "KQ";
			} else if (userAgent.indexOf(IEMOBILE) != NUMERO_MENOS_UNO) {
				resultado = "IE";
			} else if (userAgent.indexOf(SILK) != NUMERO_MENOS_UNO) {
				resultado = "SK";
			} else if (userAgent.indexOf(DOLPHIN) != NUMERO_MENOS_UNO) {
				resultado = "DP";
			} else if (userAgent.indexOf(SAFARI) != NUMERO_MENOS_UNO) {
				resultado = "SF";
			} else if (userAgent.indexOf(MOZILLA) != NUMERO_MENOS_UNO) {
				resultado = "MZ";
			} else {
				resultado = OTROS;
			}
		} catch (final Exception e) {
			resultado = OTROS;
		}
		return resultado;
	}

	/**
	 * Obtiene el atributo version navegador de UserAgent.
	 *
	 * @param pUserAgent
	 *            Parámetro user agent
	 * @return el atributo version navegador
	 */
	public static String getVersionNavegador(final String pUserAgent) {
		String pu = "";
		String resultado = "";
		try {
			if (pu != null) {
				pu = pUserAgent;
			}
			final String userAgent = pu.toLowerCase();
			// Detectamos navegador

			if (userAgent.indexOf(NETSCAPE) != NUMERO_MENOS_UNO || userAgent.indexOf("navigator") != NUMERO_MENOS_UNO
					|| userAgent.indexOf("ns8") != NUMERO_MENOS_UNO
					|| userAgent.indexOf("netscape6") != NUMERO_MENOS_UNO) {
				final int idx1 = userAgent.indexOf(NETSCAPE + "/");
				if (idx1 > NUMERO_MENOS_UNO) {
					// Posibles terminaciones para delimitar parámetros
					final Collection<String> terminaciones = new ArrayList<String>();
					terminaciones.add(";");
					terminaciones.add(")");
					terminaciones.add(" ");

					int idx2 = userAgent.length();
					for (int i = (idx1 + (NETSCAPE + "/").length()); i < userAgent.length(); i++) {
						final char ch = userAgent.charAt(i);
						if (terminaciones.contains(String.valueOf(ch))) {
							idx2 = i;
							break;
						}
					}

					// Obtenemos versión
					final String version = userAgent.substring(idx1 + (NETSCAPE + "/").length(), idx2);

					resultado = version;
				} else {
					final int idx = userAgent.indexOf("navigator/");
					if (idx > NUMERO_MENOS_UNO) {
						// Posibles terminaciones para delimitar parámetros
						final Collection<String> terminaciones = new ArrayList<String>();
						terminaciones.add(";");
						terminaciones.add(")");
						terminaciones.add(" ");

						int idx2 = userAgent.length();
						for (int i = (idx + "navigator/".length()); i < userAgent.length(); i++) {
							final char ch = userAgent.charAt(i);
							if (terminaciones.contains(String.valueOf(ch))) {
								idx2 = i;
								break;
							}
						}

						// Obtenemos versión
						final String version = userAgent.substring(idx + "navigator/".length(), idx2);

						resultado = version;
					} else {
						final int id = userAgent.indexOf("ns8/");
						if (id > NUMERO_MENOS_UNO) {
							// Posibles terminaciones para delimitar parámetros
							final Collection<String> terminaciones = new ArrayList<String>();
							terminaciones.add(";");
							terminaciones.add(")");
							terminaciones.add(" ");

							int idx2 = userAgent.length();
							for (int i = (id + "ns8/".length()); i < userAgent.length(); i++) {
								final char ch = userAgent.charAt(i);
								if (terminaciones.contains(String.valueOf(ch))) {
									idx2 = i;
									break;
								}
							}

							// Obtenemos versión
							final String version = userAgent.substring(id + "ns8/".length(), idx2);

							resultado = version;
						} else {
							final int id6 = userAgent.indexOf("netscape6/");
							if (id6 > NUMERO_MENOS_UNO) {
								// Posibles terminaciones para delimitar
								// parámetros
								final Collection<String> terminaciones = new ArrayList<String>();
								terminaciones.add(";");
								terminaciones.add(")");
								terminaciones.add(" ");

								int idx2 = userAgent.length();
								for (int i = (id6 + "netscape6/".length()); i < userAgent.length(); i++) {
									final char ch = userAgent.charAt(i);
									if (terminaciones.contains(String.valueOf(ch))) {
										idx2 = i;
										break;
									}
								}

								// Obtenemos versión
								final String version = userAgent.substring(id6 + "netscape6/".length(), idx2);

								resultado = version;
							} else {
								resultado = OTROS;
							}
						}
					}
				}

			} else if (userAgent.indexOf(CHROME) > NUMERO_MENOS_UNO) {

				final int idx1 = userAgent.indexOf(CHROME + "/");
				if (idx1 > NUMERO_MENOS_UNO) {
					// Posibles terminaciones para delimitar parámetros
					final Collection<String> terminaciones = new ArrayList<String>();
					terminaciones.add(";");
					terminaciones.add(")");
					terminaciones.add(" ");

					int idx2 = userAgent.length();
					for (int i = (idx1 + (CHROME + "/").length()); i < userAgent.length(); i++) {
						final char ch = userAgent.charAt(i);
						if (terminaciones.contains(String.valueOf(ch))) {
							idx2 = i;
							break;
						}
					}

					// Obtenemos versión
					final String version = userAgent.substring(idx1 + (CHROME + "/").length(), idx2);

					resultado = version;
				} else {
					resultado = OTROS;
				}
			} else if (userAgent.indexOf(SILK) > NUMERO_MENOS_UNO) {

				final int idx1 = userAgent.indexOf(SILK + "/");
				if (idx1 > NUMERO_MENOS_UNO) {
					// Posibles terminaciones para delimitar parámetros
					final Collection<String> terminaciones = new ArrayList<String>();
					terminaciones.add(";");
					terminaciones.add(")");
					terminaciones.add(" ");

					int idx2 = userAgent.length();
					for (int i = (idx1 + (SILK + "/").length()); i < userAgent.length(); i++) {
						final char ch = userAgent.charAt(i);
						if (terminaciones.contains(String.valueOf(ch))) {
							idx2 = i;
							break;
						}
					}

					// Obtenemos versión
					final String version = userAgent.substring(idx1 + (SILK + "/").length(), idx2);

					resultado = version;
				} else {
					resultado = OTROS;
				}

			} else if (userAgent.indexOf(SAFARI) != NUMERO_MENOS_UNO) {

				final int idx1 = userAgent.indexOf("version/");
				if (idx1 > NUMERO_MENOS_UNO) {
					// Posibles terminaciones para delimitar parámetros
					final Collection<String> terminaciones = new ArrayList<String>();
					terminaciones.add(";");
					terminaciones.add(")");
					terminaciones.add(" ");

					int idx2 = userAgent.length();
					for (int i = (idx1 + "version/".length()); i < userAgent.length(); i++) {
						final char ch = userAgent.charAt(i);
						if (terminaciones.contains(String.valueOf(ch))) {
							idx2 = i;
							break;
						}
					}

					// Obtenemos versión
					final String version = userAgent.substring(idx1 + "version/".length(), idx2);

					resultado = version;
				} else {
					final int idx = userAgent.indexOf("safari/");
					if (idx > NUMERO_MENOS_UNO) {
						// Posibles terminaciones para delimitar parámetros
						final Collection<String> terminaciones = new ArrayList<String>();
						terminaciones.add(";");
						terminaciones.add(")");
						terminaciones.add(" ");

						int idx2 = userAgent.length();
						for (int i = (idx + "safari/".length()); i < userAgent.length(); i++) {
							final char ch = userAgent.charAt(i);
							if (terminaciones.contains(String.valueOf(ch))) {
								idx2 = i;
								break;
							}
						}

						// Obtenemos versión
						final String version = userAgent.substring(idx + "safari/".length(), idx2);

						resultado = version;
					} else {
						resultado = OTROS;
					}
				}

			} else if ((userAgent.indexOf(MSIE) != NUMERO_MENOS_UNO) && (userAgent.indexOf(OPERA) == NUMERO_MENOS_UNO)
					&& (userAgent.indexOf(WEBTV) == NUMERO_MENOS_UNO)
					&& (userAgent.indexOf(IEMOBILE) == NUMERO_MENOS_UNO)) {
				final int idx1 = userAgent.indexOf(MSIE);
				final int idx2 = userAgent.indexOf(";", idx1);

				final String version = userAgent.substring(idx1 + MSIE.length() + NUMERO_UNO, idx2);
				resultado = version;

			} else if (userAgent.indexOf(OPERA) != NUMERO_MENOS_UNO) {
				final int idx1 = userAgent.indexOf(OPERA + "/");
				if (idx1 > NUMERO_MENOS_UNO) {
					// Posibles terminaciones para delimitar parámetros
					final Collection<String> terminaciones = new ArrayList<String>();
					terminaciones.add(";");
					terminaciones.add(")");
					terminaciones.add(" ");

					int idx2 = userAgent.length();
					for (int i = (idx1 + (OPERA + "/").length()); i < userAgent.length(); i++) {
						final char ch = userAgent.charAt(i);
						if (terminaciones.contains(String.valueOf(ch))) {
							idx2 = i;
							break;
						}
					}

					// Obtenemos versión
					final String version = userAgent.substring(idx1 + (OPERA + "/").length(), idx2);

					resultado = version;
				} else {
					final int idx = userAgent.indexOf("version/");
					if (idx > NUMERO_MENOS_UNO) {
						// Posibles terminaciones para delimitar parámetros
						final Collection<String> terminaciones = new ArrayList<String>();
						terminaciones.add(";");
						terminaciones.add(")");
						terminaciones.add(" ");

						int idx2 = userAgent.length();
						for (int i = (idx + "version/".length()); i < userAgent.length(); i++) {
							final char ch = userAgent.charAt(i);
							if (terminaciones.contains(String.valueOf(ch))) {
								idx2 = i;
								break;
							}
						}

						// Obtenemos versión
						final String version = userAgent.substring(idx + "version/".length(), idx2);

						resultado = version;
					} else {
						final int id = userAgent.indexOf("opera ");
						if (id > NUMERO_MENOS_UNO) {
							// Posibles terminaciones para delimitar parámetros
							final Collection<String> terminaciones = new ArrayList<String>();
							terminaciones.add(";");
							terminaciones.add(")");
							terminaciones.add(" ");

							int idx2 = userAgent.length();
							for (int i = (id + "opera ".length()); i < userAgent.length(); i++) {
								final char ch = userAgent.charAt(i);
								if (terminaciones.contains(String.valueOf(ch))) {
									idx2 = i;
									break;
								}
							}

							// Obtenemos versión
							final String version = userAgent.substring(id + "opera ".length(), idx2);

							resultado = version;
						} else {
							resultado = OTROS;
						}
					}
				}
			} else if (userAgent.indexOf(KONQUEROR) != NUMERO_MENOS_UNO) {
				final int idx1 = userAgent.indexOf(KONQUEROR + "/");
				if (idx1 > NUMERO_MENOS_UNO) {
					// Posibles terminaciones para delimitar parámetros
					final Collection<String> terminaciones = new ArrayList<String>();
					terminaciones.add(";");
					terminaciones.add(")");
					terminaciones.add(" ");

					int idx2 = userAgent.length();
					for (int i = (idx1 + (KONQUEROR + "/").length()); i < userAgent.length(); i++) {
						final char ch = userAgent.charAt(i);
						if (terminaciones.contains(String.valueOf(ch))) {
							idx2 = i;
							break;
						}
					}

					// Obtenemos versión
					final String version = userAgent.substring(idx1 + (KONQUEROR + "/").length(), idx2);

					resultado = version;
				} else {
					resultado = OTROS;
				}
			} else if (userAgent.indexOf(IEMOBILE) != NUMERO_MENOS_UNO) {
				final int idx1 = userAgent.indexOf(IEMOBILE + "/");
				if (idx1 > NUMERO_MENOS_UNO) {
					// Posibles terminaciones para delimitar parámetros
					final Collection<String> terminaciones = new ArrayList<String>();
					terminaciones.add(";");
					terminaciones.add(")");
					terminaciones.add(" ");

					int idx2 = userAgent.length();
					for (int i = (idx1 + (IEMOBILE + "/").length()); i < userAgent.length(); i++) {
						final char ch = userAgent.charAt(i);
						if (terminaciones.contains(String.valueOf(ch))) {
							idx2 = i;
							break;
						}
					}

					// Obtenemos versión
					final String version = userAgent.substring(idx1 + (IEMOBILE + "/").length(), idx2);

					resultado = version;
				} else {
					resultado = OTROS;
				}
			} else if (userAgent.indexOf(SILK) != NUMERO_MENOS_UNO) {
				final int idx1 = userAgent.indexOf(SILK + "/");
				if (idx1 > NUMERO_MENOS_UNO) {
					// Posibles terminaciones para delimitar parámetros
					final Collection<String> terminaciones = new ArrayList<String>();
					terminaciones.add(";");
					terminaciones.add(")");
					terminaciones.add(" ");

					int idx2 = userAgent.length();
					for (int i = (idx1 + (SILK + "/").length()); i < userAgent.length(); i++) {
						final char ch = userAgent.charAt(i);
						if (terminaciones.contains(String.valueOf(ch))) {
							idx2 = i;
							break;
						}
					}

					// Obtenemos versión
					final String version = userAgent.substring(idx1 + (SILK + "/").length(), idx2);

					resultado = version;
				} else {
					resultado = OTROS;
				}
			} else if (userAgent.indexOf(DOLPHIN) != NUMERO_MENOS_UNO) {
				final int idx1 = userAgent.indexOf(DOLPHIN + "/");
				if (idx1 > NUMERO_MENOS_UNO) {
					// Posibles terminaciones para delimitar parámetros
					final Collection<String> terminaciones = new ArrayList<String>();
					terminaciones.add(";");
					terminaciones.add(")");
					terminaciones.add(" ");

					int idx2 = userAgent.length();
					for (int i = (idx1 + (DOLPHIN + "/").length()); i < userAgent.length(); i++) {
						final char ch = userAgent.charAt(i);
						if (terminaciones.contains(String.valueOf(ch))) {
							idx2 = i;
							break;
						}
					}

					// Obtenemos versión
					final String version = userAgent.substring(idx1 + (DOLPHIN + "/").length(), idx2);

					resultado = version;
				} else {
					resultado = OTROS;
				}

			} else if (userAgent.indexOf(MOZILLA) != NUMERO_MENOS_UNO) {

				final int idx1 = userAgent.indexOf("firefox/");
				// comprobamos que es firefox
				if (idx1 > NUMERO_MENOS_UNO) {

					// Posibles terminaciones para delimitar parámetros
					final Collection<String> terminaciones = new ArrayList<String>();
					terminaciones.add(";");
					terminaciones.add(")");
					terminaciones.add(" ");

					int idx2 = userAgent.length();
					for (int i = (idx1 + "firefox/".length()); i < userAgent.length(); i++) {
						final char ch = userAgent.charAt(i);
						if (terminaciones.contains(String.valueOf(ch))) {
							idx2 = i;
							break;
						}
					}

					// Obtenemos versión
					final String version = userAgent.substring(idx1 + "firefox/".length(), idx2);

					resultado = version;
				} else {
					resultado = OTROS;
				}

			} else {
				resultado = OTROS;
			}
		} catch (final Exception e) {
			resultado = null;
		}
		return resultado;
	}

	/**
	 * Obtiene el atributo sistema operativo de UserAgent.
	 *
	 * @param pUserAgent
	 *            Parámetro user agent
	 * @return el atributo sistema operativo
	 */
	public static String getSistemaOperativo(final String pUserAgent) {
		String pu = "";
		String resultado = "";
		try {
			if (pu != null) {
				pu = pUserAgent;
			}
			final String userAgent = pu.toLowerCase();
			// Detectamos S.O.
			if (userAgent.indexOf("android") != NUMERO_MENOS_UNO) {
				resultado = "ANDROID";
			} else if (userAgent.indexOf("linux") != NUMERO_MENOS_UNO) {
				resultado = "LINUX";
			} else if (userAgent.indexOf("macintosh") != NUMERO_MENOS_UNO) {
				resultado = "MAC";
			} else if ((userAgent.indexOf("windows phone") != NUMERO_MENOS_UNO)
					|| (userAgent.indexOf("win") != NUMERO_MENOS_UNO)) {
				resultado = "WIN";
			} else if (userAgent.indexOf("iphone") != NUMERO_MENOS_UNO) {
				resultado = "IPHONE";
			} else if (userAgent.indexOf("webos") != NUMERO_MENOS_UNO) {
				resultado = "WEBOS";
			} else if (userAgent.indexOf("palmos") != NUMERO_MENOS_UNO) {
				resultado = "PALM";
			} else if (userAgent.indexOf("ipad") != NUMERO_MENOS_UNO) {
				resultado = "IPAD";
			} else if (userAgent.indexOf("ipod") != NUMERO_MENOS_UNO) {
				resultado = "IPOD";
			} else if (userAgent.indexOf("symbian") != NUMERO_MENOS_UNO) {
				resultado = "SY";
			} else if (userAgent.indexOf("sonyericsson") != NUMERO_MENOS_UNO) {
				resultado = "SE";
			} else if (userAgent.indexOf("sunos") != NUMERO_MENOS_UNO) {
				resultado = "SUNOS";
			} else if (userAgent.indexOf("playstation") != NUMERO_MENOS_UNO) {
				resultado = "PSP";
			} else if (userAgent.indexOf("wii") != NUMERO_MENOS_UNO) {
				resultado = "WII";
			} else if ((userAgent.indexOf("bb10") != NUMERO_MENOS_UNO)
					|| (userAgent.indexOf("playbook") != NUMERO_MENOS_UNO)
					|| (userAgent.indexOf("blackberry") != NUMERO_MENOS_UNO)) {
				resultado = "BB";

			} else {
				resultado = OTROS;
			}
		} catch (final Exception e) {
			resultado = null;
		}
		return resultado;
	}

	/**
	 * Comprueba si es un dispositivo móvil basandose en el user agent.
	 *
	 * @param pUserAgent
	 *            User agent
	 * @return true si es un dispositivo móvil
	 */
	public static boolean esDispositivoMovil(final String pUserAgent) {
		final String so = getSistemaOperativo(pUserAgent);
		return esSistemaOperativoMovil(so);
	}

	/**
	 * Verifica si el sistema operativo es de un móvil.
	 *
	 * @param so
	 *            Sistema operativo
	 * @return true si es un dispositivo móvil
	 */
	private static boolean esSistemaOperativoMovil(final String so) {
		return SO_BLACKBERRY.equals(so) || SO_SONYERICSSON.equals(so) || SO_SYMBIAN.equals(so) || SO_IPOD.equals(so)
				|| SO_IPAD.equals(so) || SO_PALM.equals(so) || SO_WEBOS.equals(so) || SO_IPHONE.equals(so)
				|| SO_ANDROID.equals(so);
	}

	public static String getVersionSistemaOperativo(final String sistemaOperativo, final String pUserAgent) {
		String version = OTROS;
		if (SO_ANDROID.equals(sistemaOperativo)) {
			final int indx = pUserAgent.indexOf("Android ");
			if (indx > -1) {
				final int indxVersion = pUserAgent.indexOf(";", indx + "Android ".length());
				if (indxVersion > -1) {
					version = pUserAgent.substring(indx + "Android ".length(), indxVersion);

				}
			}
		} else if (SO_WINDOWS.equals(sistemaOperativo)) {
			if (pUserAgent.toLowerCase().indexOf("6.1") > -1) {
				version = "7";
			} else if (pUserAgent.toLowerCase().indexOf("6.2") > -1) {
				version = "8";
			} else if (pUserAgent.toLowerCase().indexOf("6.0") > -1) {
				version = "Vista";
			} else if (pUserAgent.toLowerCase().indexOf("5.2") > -1) {
				version = "XP64";
			} else if (pUserAgent.toLowerCase().indexOf("5.1") > -1) {
				version = "XP2";
			} else if (pUserAgent.toLowerCase().indexOf("XP") > -1) {
				version = "XP";
			} else if (pUserAgent.toLowerCase().indexOf("5.0") > -1) {
				version = "2K";
			} else if (pUserAgent.toLowerCase().indexOf("4.0") > -1) {
				version = "NY";
			} else if (pUserAgent.toLowerCase().indexOf("98") > -1) {
				version = "98";
			} else if (pUserAgent.toLowerCase().indexOf("95") > -1) {
				version = "95";
			} else if (pUserAgent.toLowerCase().indexOf("CE") > -1) {
				version = "CE";
			}
		} else if (SO_IPHONE.equals(sistemaOperativo)) {
			final int indx = pUserAgent.indexOf("OS ");
			if (indx > -1) {
				final int indxVersion = pUserAgent.indexOf(" ", indx + 3);
				if (indxVersion > -1) {
					version = pUserAgent.substring(indx + 3, indxVersion);

				}
			}
		} else if (SO_IPAD.equals(sistemaOperativo)) {
			final int indx = pUserAgent.indexOf("OS ");
			if (indx > -1) {
				final int indxVersion = pUserAgent.indexOf(" ", indx + 3);
				if (indxVersion > -1) {
					version = pUserAgent.substring(indx + 3, indxVersion);

				}
			}
		} else if (SO_BLACKBERRY.equals(sistemaOperativo)) {
			final int indx = pUserAgent.indexOf("Version");
			if (indx > -1) {
				final int indx2 = pUserAgent.indexOf("/", indx);
				if (indx2 > -1) {
					final int indx3 = pUserAgent.indexOf(" ", indx2);
					version = pUserAgent.substring(indx2 + 1, indx3);
				}
			}
		} else if (SO_SYMBIAN.equals(sistemaOperativo)) {
			final int indx = pUserAgent.indexOf("SymbianOS");
			if (indx > -1) {
				final int indx2 = pUserAgent.indexOf("/", indx);
				if (indx2 > -1) {
					final int indx3 = pUserAgent.indexOf(";", indx2);
					version = pUserAgent.substring(indx2 + 1, indx3);
				}
			}
		} else if (SO_LINUX.equals(sistemaOperativo)) {
			int indx = pUserAgent.indexOf("Ubuntu");
			if (indx > -1) {
				final int indx2 = pUserAgent.indexOf(" ", indx + "Ubuntu".length());
				if (indx2 > -1) {
					version = pUserAgent.substring(indx, indx2);
				} else {
					version = "Ubuntu";
				}
			} else {
				indx = pUserAgent.indexOf("SUSE");
				if (indx > -1) {
					final int indx2 = pUserAgent.indexOf(" ", indx + "SUSE".length());
					if (indx2 > -1) {
						version = pUserAgent.substring(indx, indx2);
					} else {
						version = "SUSE";
					}
				} else {
					indx = pUserAgent.indexOf("Red Hat");
					if (indx > -1) {
						final int indx2 = pUserAgent.indexOf(" ", indx + "Red Hat".length());
						if (indx2 > -1) {
							version = pUserAgent.substring(indx, indx2);
						} else {
							version = "Red Hat";
						}
					} else {
						indx = pUserAgent.indexOf("Fedora");
						if (indx > -1) {
							final int indx2 = pUserAgent.indexOf(" ", indx + "Fedora".length());
							if (indx2 > -1) {
								version = pUserAgent.substring(indx, indx2);
							} else {
								version = "Fedora";
							}
						} else {
							indx = pUserAgent.indexOf("Debian");
							if (indx > -1) {
								version = "Debian";
							}
						}
					}
				}
			}
		} else if (SO_MAC.equals(sistemaOperativo)) {

			final int indx = pUserAgent.indexOf("OS X ");
			if (indx > -1) {
				final int indxVersion = pUserAgent.indexOf(")", indx + "OS X ".length());
				if (indxVersion > -1) {
					version = pUserAgent.substring(indx + "OS X ".length(), indxVersion);

				}
			}
		}

		return StringUtils.replace(version, "_", ".");
	}

}
