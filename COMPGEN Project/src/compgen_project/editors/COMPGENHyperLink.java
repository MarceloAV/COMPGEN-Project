package compgen_project.editors;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;

public class COMPGENHyperLink implements IHyperlink {

	private final IRegion fUrlRegion;

	public COMPGENHyperLink(IRegion iRegion) {
		fUrlRegion = iRegion;
	}

	@Override
	public IRegion getHyperlinkRegion() {
		return fUrlRegion;
	}

	@Override
	public String getHyperlinkText() {
		return this.fUrlRegion.toString();
	}

	@Override
	public String getTypeLabel() {
		return null;
	}

	@Override
	public void open() {
		new COMPGENEditor().setHighlightRange(this.fUrlRegion.getOffset(), this.fUrlRegion.getLength(), false);
	}
}
