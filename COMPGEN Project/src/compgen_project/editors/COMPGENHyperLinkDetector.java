package compgen_project.editors;

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

}
