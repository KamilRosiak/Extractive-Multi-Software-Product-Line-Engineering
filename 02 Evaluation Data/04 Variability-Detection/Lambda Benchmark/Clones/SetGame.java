
public class SetGame {
		/**
	 * wird aufgerufen, wenn man auf den TutorialButton geht
	 */
	private void setTutorial() {
		super.setBWin(false);
		this.bMenu = false;
		
		this.bGame = false;
		this.bTutorial = true;
		this.bEditor = false;
		for (int i = 0; i < ApoBotConstants.BUTTON_TUTORIAL.length && i < super.getButtons().length; i++) {
			super.getButtons()[i].setBVisible(ApoBotConstants.BUTTON_TUTORIAL[i]);
		}
		if (ApoConstants.B_APPLET) {
			super.getButtons()[0].setBVisible(false);
		}
		if (super.getButtons()[13].isBVisible()) {
			if (this.bGo) {
				super.getButtons()[4].setBVisible(false);
			} else {
				super.getButtons()[13].setBVisible(false);
			}
		}
		this.tutorial.init();
	}

	/**
	 * wird aufgerufen, wenn man auf den EditorButton geht
	 */
	private void setEditor() {
		super.setBWin(false);
		this.bMenu = false;
		this.bGame = false;
		this.bTutorial = false;
		this.bEditor = true;
		this.editor.init();
		for (int i = 0; i < ApoBotConstants.BUTTON_EDITOR.length && i < super.getButtons().length; i++) {
			super.getButtons()[i].setBVisible(ApoBotConstants.BUTTON_EDITOR[i]);
		}
		if (ApoConstants.B_APPLET) {
			super.getButtons()[0].setBVisible(false);

			super.getButtons()[27].setBVisible(false);
			super.getButtons()[28].setBVisible(false);
		}
		super.getButtons()[4].setBVisible(false);
		super.getButtons()[13].setBVisible(false);
	}
}

