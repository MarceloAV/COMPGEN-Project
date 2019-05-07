package compgen_project.scanner;

import org.eclipse.jface.text.*;
import org.eclipse.jface.text.rules.*;

import compgen_project.editors.ColorManager;
import compgen_project.editors.COMPGENColorConstants;
import compgen_project.editors.COMPGENWhitespaceDetector;


public class COMPGENBracketScanner extends RuleBasedScanner {

	public COMPGENBracketScanner(ColorManager manager) {

		IToken string = new Token(new TextAttribute(manager.getColor(COMPGENColorConstants.STRING)));
		IToken character = new Token(new TextAttribute(manager.getColor(COMPGENColorConstants.CHARACTER)));
		IToken comment = new Token(new TextAttribute(manager.getColor(COMPGENColorConstants.COMPGEN_COMMENT)));
		IToken tokens = new Token(new TextAttribute(manager.getColor(COMPGENColorConstants.TOKEN)));
		IToken ids = new Token(new TextAttribute(manager.getColor(COMPGENColorConstants.IDS)));
		IToken number = new Token(new TextAttribute(manager.getColor(COMPGENColorConstants.NUMBER)));
		
		IRule[] rules = new IRule[24];
		
		rules[0] = new WordRule(new NumberDetector(), number);
		rules[1] = new WordPatternRule(new TokenDetector(),"start", "", tokens);
		rules[2] = new WordPatternRule(new TokenDetector(),"language", "", tokens);
		rules[3] = new WordPatternRule(new TokenDetector(),"class", "", tokens);
		rules[4] = new WordPatternRule(new TokenDetector(),"compile", "", tokens);
		rules[5] = new WordPatternRule(new TokenDetector(),"extends", "", tokens);
		rules[6] = new WordPatternRule(new TokenDetector(),"syntax", "", tokens);
		rules[7] = new WordPatternRule(new TokenDetector(),"this", "", tokens);
		rules[8] = new WordPatternRule(new TokenDetector(),"val", "", tokens);
		rules[9] = new WordPatternRule(new TokenDetector(),"eval", "", tokens);
		rules[10] = new WordPatternRule(new TokenDetector(),"asm", "", tokens);
		rules[11] = new WordPatternRule(new TokenDetector(),"print", "", tokens);
		rules[12] = new WordPatternRule(new TokenDetector(),"forEach", "", tokens);
		rules[13] = new WordPatternRule(new TokenDetector(), "nextLabel", "", tokens);
		rules[14] = new WordPatternRule(new TokenDetector(),"opCodeOf", "", tokens);
		rules[15] = new WordPatternRule(new TokenDetector(),"toString", "", tokens);
		rules[16] = new WordPatternRule(new TokenDetector(),"lexical", "", tokens);
		rules[17] = new WordPatternRule(new TokenDetector(),"whitespace", "", tokens);
		rules[18] = new WordPatternRule(new TokenDetector(),"var", "", tokens);
		
		rules[19] = new WordRule(new IdDetector(), ids);
		
		rules[20] = new SingleLineRule("\"", "\"", string);
		rules[21] = new SingleLineRule("'", "'", character);
		rules[22] = new WhitespaceRule(new COMPGENWhitespaceDetector());
		rules[23] = new MultiLineRule("/*", "*/", comment);


		
		setRules(rules);
	}
}
