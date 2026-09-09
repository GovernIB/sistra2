package es.caib.sistrages.frontend.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UtilVariablesArea {

	private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{@@([A-Za-z0-9_-]+)@@\\}");

	private UtilVariablesArea() {
	}

	public static List<String> obtenerIdentificadores(final String url) {
		final List<String> identificadores = new ArrayList<>();
		if (url != null) {
			final Matcher matcher = PLACEHOLDER_PATTERN.matcher(url);
			while (matcher.find()) {
				identificadores.add(matcher.group(1));
			}
		}
		return identificadores;
	}
}
