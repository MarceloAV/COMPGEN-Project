package compgen_project.scanner;

import org.eclipse.jface.text.rules.IWordDetector;

public class IdDetector implements IWordDetector {

	@Override
	public boolean isWordPart(char arg0) {
		return Character.isLetter(arg0) || Character.isDigit(arg0);
	}

	@Override
	public boolean isWordStart(char arg0) {
		return Character.isLetter(arg0);
	}
}
