class DrawBox {
	private void drawInputRightBox(final BitsGraphics g, final boolean bActive) {
		int x = 55;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bActive);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		for (int i = 0; i < 10; i++) {
			g.fillRect(x + 10 + i * 2, y + 10 + i * 1, 2, 20 - i * 2);
		}
	}
	
	private void drawInputLeftBox(final BitsGraphics g, final boolean bActive) {
		int x = 5;
		int y = ApoMonoPuzzleGame.changeY + 245;
		this.drawInputBox(g, x, y, bActive);
		g.setColor(ApoMonoConstants.BRIGHT[0], ApoMonoConstants.BRIGHT[1], ApoMonoConstants.BRIGHT[2], 1f);
		for (int i = 0; i < 10; i++) {
			g.fillRect(x + 30 - i * 2, y + 10 + i * 1, 2, 20 - i * 2);
		}
	}
	
	
}