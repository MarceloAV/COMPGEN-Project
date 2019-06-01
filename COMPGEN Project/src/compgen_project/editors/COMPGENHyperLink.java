package compgen_project.editors;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;

public class COMPGENHyperLink implements IHyperlink {

	private COMPGENEditor aEditor;
	private final IRegion aRegion;
	private IRegion aTargetRegion;

	public COMPGENHyperLink(COMPGENEditor pEditor, IRegion pRegion, IRegion pTargetRegion) {
		this.aEditor = pEditor;
		this.aRegion = pRegion;
		this.aTargetRegion = pTargetRegion;
	}

	@Override
	public IRegion getHyperlinkRegion() {
		return aRegion;
	}

	@Override
	public String getHyperlinkText() {
		return this.aRegion.toString();
	}

	@Override
	public String getTypeLabel() {
		return null;
	}

	@Override
	public void open() {
		this.aEditor.setHighlightRange(this.aTargetRegion.getOffset(), this.aTargetRegion.getLength(), true);
	}
}
