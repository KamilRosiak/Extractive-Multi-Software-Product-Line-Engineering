package apoMario.game.panels;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.apogames.entity.ApoAnimation;

import apoMario.game.ApoMarioPanel;
import apoMario.game.ApoMarioSearch;
import apoMario.game.ApoMarioSearchNode;
import apoMario.game.ApoMarioSearchRunner;

/**
 * abstrakte MenuPanelklasse<br />
 * abstrakte Klasse, die das Grundgerüst stellt, um das Menu im Mario Style zu machen<br />
 * also um mit dem Spieler rumzulaufen<br />
 * davon erben alle anderen Panelklassen und diese Klasse selber erbt von ApoMarioModel<br />
 * @author Dirk Aporius
 */
public abstract class ApoMarioModelMenu extends ApoMarioModel {

	private BufferedImage iBackground;
	
	private ApoMarioSearch search;
	
	private String excecuteFunction;
	
	private int endNode = 1;
	
	private ApoMarioSearchRunner runner;
	
	private ArrayList<ApoMarioSearchNode> way;
	
	private int curNode;
	
	private ArrayList<ApoAnimation> backgroundAnimation;
	
	public ApoMarioModelMenu(ApoMarioPanel game) {
		super(game);
	}
	
	public void init() {
		this.makeBackground();
		this.makeBackgroundAnimation();
		this.makeRunner();
		this.makeSearch();
		
		this.setEndNode(-1);
		
		this.setWay(null);
		this.excecuteFunction = null;
	}
	
	public BufferedImage getIBackground() {
		return this.iBackground;
	}

	public void setIBackground(BufferedImage background) {
		this.iBackground = background;
	}

	public ApoMarioSearch getSearch() {
		return this.search;
	}

	public void setSearch(ApoMarioSearch search) {
		this.search = search;
	}

	public String getExcecuteFunction() {
		return this.excecuteFunction;
	}

	public void setExcecuteFunction(String excecuteFunction) {
		this.excecuteFunction = excecuteFunction;
	}

	public int getEndNode() {
		return this.endNode;
	}

	public void setEndNode(int endNode) {
		this.endNode = endNode;
	}

	public ApoMarioSearchRunner getRunner() {
		return this.runner;
	}

	public void setRunner(ApoMarioSearchRunner runner) {
		this.runner = runner;
	}

	public ArrayList<ApoMarioSearchNode> getWay() {
		return this.way;
	}

	public void setWay(ArrayList<ApoMarioSearchNode> way) {
		this.way = way;
	}

	public int getCurNode() {
		return this.curNode;
	}

	public void setCurNode(int curNode) {
		this.curNode = curNode;
	}

	public ArrayList<ApoAnimation> getBackgroundAnimation() {
		return this.backgroundAnimation;
	}

	public void setBackgroundAnimation(ArrayList<ApoAnimation> backgroundAnimation) {
		this.backgroundAnimation = backgroundAnimation;
	}

	public void searchWay() {
		if (this.endNode != -1) {
			this.way = this.search.searchWay(this.curNode, this.endNode);
			if ((this.way != null) && (this.way.size() > 0)) {
				this.runner.setEnd(this.way.get(0).getX(), this.way.get(0).getY());
			}
		}
	}
	
	public abstract void makeBackground();
	
	public abstract void makeRunner();
	
	public abstract void makeBackgroundAnimation();
	
	public abstract void makeSearch();
	
	public abstract void excecuteFunction();
	
	public void keyButtonReleasedArrowAndEnter(int button, char character) {
		ApoMarioSearchNode node = this.search.getNodes().get(this.curNode);
		int value = -1;
		if (button == KeyEvent.VK_LEFT) {
			value = 0;
		} else if (button == KeyEvent.VK_RIGHT) {
			value = 1;
		} else if (button == KeyEvent.VK_UP) {
			value = 2;
		} else if (button == KeyEvent.VK_DOWN) {
			value = 3;
		}
		
		for (int i = 0; i < node.getConntectedNodes().size(); i++) {
			ApoMarioSearchNode connectedNode = this.search.getNodes().get(node.getConntectedNodes().get(i));
			if ( ((value == 0) && (connectedNode.getX() < node.getX()) && (connectedNode.getY() == node.getY())) ||
				 ((value == 1) && (connectedNode.getX() > node.getX()) && (connectedNode.getY() == node.getY())) ||
				 ((value == 2) && (connectedNode.getY() < node.getY()) && (connectedNode.getX() == node.getX())) ||
				 ((value == 3) && (connectedNode.getY() > node.getY()) && (connectedNode.getX() == node.getX())) ) {
				this.way = new ArrayList<ApoMarioSearchNode>();
				this.way.add(node);
				this.way.add(connectedNode);
				this.endNode = connectedNode.getNode();
				this.excecuteFunction = null;
				return;
			}
		}
		
		if (button == KeyEvent.VK_ENTER) {
			this.releasedEnter();
		}
	}
	
	public void releasedEnter() {
		
	}
	
	public void thinkRunnerAndAnimation(int delta) {
		if (this.backgroundAnimation != null) {
			for (int i = 0; i < this.backgroundAnimation.size(); i++) {
				this.backgroundAnimation.get(i).think(delta);
			}
		}
		if (this.runner != null) {
			if ((this.way != null) && (this.way.size() > 0)) {
				if (this.runner.hasReachedEnd()) {
					int node = this.way.get(0).getNode();
					this.way.remove(0);
					if (this.way.size() > 0) {
						this.runner.setEnd(this.way.get(0).getX(), this.way.get(0).getY());
						this.runner.think(delta);
						this.curNode = this.way.get(0).getNode();
					} else {
						this.curNode = node;
						this.excecuteFunction();
					}
				} else {
					this.runner.think(delta);
				}
			}
		}
	}

	public void renderBackgroundAndAnimation(Graphics2D g) {
		if (this.iBackground != null) {
			g.drawImage(this.iBackground, 0, 0, null);
		}
		if (this.backgroundAnimation != null) {
			for (int i = 0; i < this.backgroundAnimation.size(); i++) {
				this.backgroundAnimation.get(i).render(g, 0, 0);
			}
		}
	}
	
	public void renderButtonsAndRunner(Graphics2D g) {
		this.getGame().renderButtons(g);
		if (this.runner != null) {
			this.runner.render(g, 0, 0);
		}
	}
}
