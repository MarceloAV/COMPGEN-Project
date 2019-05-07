package compgen_project.scanner;

import org.eclipse.jface.text.rules.*;

public class COMPGENPartitionScanner extends RuleBasedPartitionScanner {
	public final static String COMPGEN_COMMENT = "__compgen_comment";
	public final static String COMPGEN_BRACKETS = "__compgen_brackets";

	public COMPGENPartitionScanner() {

		IToken impComment = new Token(COMPGEN_COMMENT);

		IPredicateRule[] rules = new IPredicateRule[1];

		rules[0] = new MultiLineRule("/*", "*/", impComment);


		setPredicateRules(rules);
	}
}
