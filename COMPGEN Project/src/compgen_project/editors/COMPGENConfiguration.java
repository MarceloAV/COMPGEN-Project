package compgen_project.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.DefaultAnnotationHover;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import compgen_project.scanner.COMPGENBracketScanner;
import compgen_project.scanner.COMPGENPartitionScanner;

public class COMPGENConfiguration extends SourceViewerConfiguration {
	private COMPGENDoubleClickStrategy doubleClickStrategy;
	private COMPGENBracketScanner bracketScanner;
	private ColorManager colorManager;
	private COMPGENEditor editor;

	public COMPGENConfiguration(ColorManager colorManager, COMPGENEditor editor) {
		this.colorManager = colorManager;
		this.editor = editor;
	}

	@Override
	public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
		// TODO Auto-generated method stub
		return new DefaultAnnotationHover();
	}

	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE, COMPGENPartitionScanner.COMPGEN_BRACKETS };
	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new COMPGENDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected COMPGENBracketScanner getIMPBracketsScanner() {
		if (bracketScanner == null) {
			bracketScanner = new COMPGENBracketScanner(colorManager);
			bracketScanner.setDefaultReturnToken(
					new Token(new TextAttribute(colorManager.getColor(COMPGENColorConstants.BRACKET))));
		}
		return bracketScanner;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(getIMPBracketsScanner());
		reconciler.setDamager(dr, COMPGENPartitionScanner.COMPGEN_BRACKETS);
		reconciler.setRepairer(dr, COMPGENPartitionScanner.COMPGEN_BRACKETS);

		dr = new DefaultDamagerRepairer(getIMPBracketsScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(
				new TextAttribute(colorManager.getColor(COMPGENColorConstants.COMPGEN_COMMENT)));
		reconciler.setDamager(ndr, COMPGENPartitionScanner.COMPGEN_COMMENT);
		reconciler.setRepairer(ndr, COMPGENPartitionScanner.COMPGEN_COMMENT);

		return reconciler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.text.source.SourceViewerConfiguration#getContentAssistant(
	 * org.eclipse.jface.text.source.ISourceViewer)
	 */
	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
		ContentAssistant assistant = new ContentAssistant();
		assistant.setContentAssistProcessor(new COMPGENContentAssistantProcessor(), IDocument.DEFAULT_CONTENT_TYPE);
		assistant.enableAutoActivation(true);

		return assistant;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.text.source.SourceViewerConfiguration#getReconciler(org.
	 * eclipse.jface.text.source.ISourceViewer)
	 */
	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		COMPGENReconcilingStrategy strategy = new COMPGENReconcilingStrategy();
		strategy.setEditor(editor);

		MonoReconciler reconciler = new MonoReconciler(strategy, false);

		return reconciler;
	}

}