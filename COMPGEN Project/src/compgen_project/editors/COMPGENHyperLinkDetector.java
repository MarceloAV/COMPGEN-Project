package compgen_project.editors;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;

public class COMPGENHyperLinkDetector implements IHyperlink {
	
	private final IRegion fUrlRegion;
	
	public COMPGENHyperLinkDetector (IRegion iRegion) {
		fUrlRegion = iRegion;
	}

	@Override
	public IRegion getHyperlinkRegion() {
		return fUrlRegion;
	}

	@Override
	public String getHyperlinkText() {
		return null;
	}

	@Override
	public String getTypeLabel() {
		return null;
	}

	@Override
	public void open() {

	}

}
