package compgen_project.editors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;

public class COMPGENHyperLinkDetector implements IHyperlinkDetector {

	private static final String WORD = "word";

	@Override
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
		IDocument document = textViewer.getDocument();
		int offset = region.getOffset();

		Hashtable<String, Region> indice = new Hashtable<String, Region>();
		String text = document.get();

		// extract relevant characters
		IRegion lineRegion;
		String candidate;
		try {
			lineRegion = document.getLineInformationOfOffset(offset);
			candidate = document.get(lineRegion.getOffset(), lineRegion.getLength());
		} catch (BadLocationException ex) {
			return null;
		}

		// look for keyword
		int index = candidate.indexOf(WORD);
		if (index != -1) {

			// detect region containing keyword
			IRegion targetRegion = new Region(lineRegion.getOffset() + index, WORD.length());
			if ((targetRegion.getOffset() <= offset)
					&& ((targetRegion.getOffset() + targetRegion.getLength()) > offset))
				// create link
				return new IHyperlink[] { new COMPGENHyperLink(targetRegion) };

		}

		return null;
	}

	public static void main(String[] args) {
		String code = "language Imp { \r\n" + "    \r\n" + "    \r\n" + "    class Com {\r\n" + "\r\n"
				+ "       compile(dest) {\r\n" + "       }\r\n" + "    }\r\n" + "    \r\n"
				+ "    class Assign extends Com {\r\n" + "       syntax [[ id:ID ':=' val:Exp ';' ]]\r\n"
				+ "           \r\n" + "       compile(asm) {\r\n" + "            this.val.eval(asm);\r\n"
				+ "            asm.print(\"store \" + this.id);\r\n" + "       }\r\n" + "    }\r\n" + "    \r\n"
				+ "    class Block extends Com {\r\n" + "       syntax [[ \"{\" coms:Com* '}' ]]\r\n"
				+ "       compile(asm) {\r\n" + "           this.coms.forEach(c => c.compile(asm));\r\n"
				+ "       }\r\n" + "    }\r\n" + "    class While extends Com {\r\n"
				+ "       syntax [[ \"while\" test:Exp \"do\" c:Com ]]\r\n" + "       \r\n"
				+ "       compile(asm) {\r\n" + "          var l1 = asm.nextLabel();\r\n"
				+ "          var l2 = asm.nextLabel();\r\n" + "          asm.print(l1+\":\");\r\n"
				+ "          this.test.eval(asm);\r\n" + "          asm.print(\"jumpf \"+l2);\r\n"
				+ "          this.c.compile(asm);\r\n" + "          asm.print(\"jump \"+l1);\r\n"
				+ "          asm.print(l2+\":\");\r\n" + "       }\r\n" + "    }\r\n" + "    class Exp { \r\n"
				+ "    }\r\n" + "\r\n" + "    class Id extends Exp {\r\n" + "       syntax [[ id:ID ]]\r\n"
				+ "       \r\n" + "       eval(dest) { dest.print(\"load \" +this.id); } \r\n" + "    }\r\n"
				+ "    \r\n" + "    class Op extends Exp {\r\n" + "       syntax [[ left:Exp o:OP right:Exp ]]\r\n"
				+ "       \r\n" + "       eval(asm) { \r\n" + "          this.left.eval(asm);\r\n"
				+ "          this.right.eval(asm);\r\n" + "          asm.opCodeOf(this.o.toString());\r\n"
				+ "       }\r\n" + "    }\r\n" + "    \r\n" + "    class Group extends Exp {\r\n"
				+ "       syntax [[ '(' e:Exp ')' ]]\r\n" + "       \r\n" + "       eval(asm) { this.e.eval(asm); }\r\n"
				+ "    }\r\n" + "    class Num extends Exp {\r\n" + "       syntax [[ val:NUM ]] \r\n"
				+ "       eval(dest) { \r\n" + "          dest.print(\"push \" + this.val);\r\n" + "       } \r\n"
				+ "    }\r\n" + "    lexical {\r\n" + "       OP '\\+|\\-|\\*|/|(==)|(!=)|(>=)|>|<|(<=)' '';\r\n"
				+ "       NUM '[0-9]+' 'color=green';\r\n" + "       ID  '[a-z]+' 'color=blue';\r\n" + "    }\r\n"
				+ "    \r\n" + "    \r\n" + "    whitespace = '[ \\n\\t]+';\r\n" + "    start = Com;\r\n" + "}\r\n"
				+ "";

		String delimiterKeyWords = "|language|class|compile|extends|syntax|this|val|eval|print|asm|forEach|nextLabel|opCodeOf|toString|lexical|whitespace|start|var";
		String delimiterArithmeticOperators = "|\\+|\\-|\\*|\\/|%|\\+\\+|--";
		String delimiterRelationalOperators = "|(!=|==|<=|>=|>|<)";
		String delimiterLogicalOperators = "|&&|\\|\\||<=|>=|>|<";
		String delimiterAssignmentOperators = "|=|\\+=|-=|\\*=|/=|%=";
		String delimiterSeparators = "|\\(|\\)|\\{|\\}|\\[|\\]|;|\\.|'(.*?)'|:";
		String delimiterStringLiterals = "|\\\"([^\\\\\\\"]|\\\\.)*\\\"";

		String delimiters = "\\s+" + delimiterKeyWords + delimiterArithmeticOperators + delimiterRelationalOperators
				+ delimiterLogicalOperators + delimiterAssignmentOperators + delimiterSeparators
				+ delimiterStringLiterals;

		String[] tokensEncontrados = code.split(delimiters);
		ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(tokensEncontrados));
		tokens.removeAll(Arrays.asList("", null));

		int offset = -1;
		for (String tokenAtual : tokens) {
			offset = code.indexOf(tokenAtual, offset + 1);
			System.out.println("[" + tokenAtual + "] - Offset em " + offset);
		}

	}

	public static boolean matchKeyWords(String pText) {
		return pText.matches(
				"(language|class|compile|extends|syntax|this|val|eval|print|asm|forEach|nextLabel|opCodeOf|toString|lexical|whitespace|start|var)");
	}

	public static boolean matchArithmeticOperators(String pText) {
		return pText.matches("\\+|\\-|\\*|\\/|%|\\+\\+|--");
	}

	public static boolean matchRelationalOperators(String pText) {
		return pText.matches("!=|==|<=|>=|>|<");
	}

	public static boolean matchLogicalOperators(String pText) {
		return pText.matches("&&|(\\|\\|)|<=|>=|>|<");
	}

	public static boolean matchAssignmentOperators(String pText) {
		return pText.matches("=|\\+=|-=|\\*=|/=|%=");
	}

	public static boolean matchSeparators(String pText) {
		return pText.matches("|\\(|\\)|\\{|\\}|\\[|\\]|;|\\.|'(.*?)'|:");
	}

	public static boolean matchLiterals(String pText) {
		return pText.matches("(?:\\b[_a-zA-Z]|\\B\\$)[_$a-zA-Z0-9]*+");
	}

	public static boolean matchOperators(String pText) {
		return matchArithmeticOperators(pText) || matchRelationalOperators(pText) || matchLogicalOperators(pText)
				|| matchAssignmentOperators(pText);
	}

}
