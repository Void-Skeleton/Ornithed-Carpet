package org.carpet.command.data;

import org.carpet.utils.algebraic.Algebraic;
import org.carpet.utils.algebraic.MatchWith;

import static org.carpet.command.data.CarpetCommandData.*;

@Algebraic({SetDefault.class, Write.class, Query.class, ListSettings.class})
public interface CarpetCommandData {
	class SetDefault implements CarpetCommandData {
		private Void setDefault;
		public String ruleName;
		public String ruleValue;
	}

	class Write implements CarpetCommandData {
		public String ruleName;
		public String ruleValue;
	}

	class Query implements CarpetCommandData {
		public String ruleName;
	}

	class ListSettings implements CarpetCommandData {
	}
}
