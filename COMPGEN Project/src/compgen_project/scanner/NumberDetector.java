package compgen_project.scanner;

import org.eclipse.jface.text.rules.IWordDetector;

public class NumberDetector implements IWordDetector {

	@Override
	public boolean isWordPart(char arg0) {
		return Character.isDigit(arg0);
	}

	@Override
	public boolean isWordStart(char arg0) {
		return Character.isDigit(arg0);
	}

}