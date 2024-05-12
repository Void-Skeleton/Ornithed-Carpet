package carpet.utils.algebraic;

import java.util.List;

public class StringAdtMatcher extends SingleAdtMatcher<String> {
	@Override
	String parseDirectly(String raw) {
		return raw;
	}
}
