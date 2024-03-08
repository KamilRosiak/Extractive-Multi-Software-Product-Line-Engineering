package apoSoccer.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import apoSoccer.ApoSoccerConstants;
import apoSoccer.ApoSoccerImages;
import apoSoccer.entity.ApoBall;
import apoSoccer.entity.ApoPlayer;
import apoSoccer.entity.ApoTeam;

/**
 * Klasse, die das DebugFrame repräsentiert
 * @author Dirk Aporius
 *
 */
public class ApoSoccerDebugFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JLabel 	homeGoalkeeperName, homeGoalkeeperPosition, homeGoalkeeperSpeed, homeGoalkeeperLos,
					homeGoalkeeperFreshness, homeGoalkeeperMaxFreshness, homeGoalkeeperShootable;
	private JLabel 	homeForwardName, homeForwardPosition, homeForwardSpeed, homeForwardLos,
					homeForwardFreshness, homeForwardMaxFreshness, homeForwardShootable;
	private JLabel 	homeDefenderOneName, homeDefenderOnePosition, homeDefenderOneSpeed, homeDefenderOneLos,
					homeDefenderOneFreshness, homeDefenderOneMaxFreshness, homeDefenderOneShootable;
	private JLabel 	homeDefenderTwoName, homeDefenderTwoPosition, homeDefenderTwoSpeed, homeDefenderTwoLos,
					homeDefenderTwoFreshness, homeDefenderTwoMaxFreshness, homeDefenderTwoShootable;

	private JLabel 	visitorGoalkeeperName, visitorGoalkeeperPosition, visitorGoalkeeperSpeed, visitorGoalkeeperLos,
					visitorGoalkeeperFreshness, visitorGoalkeeperMaxFreshness, visitorGoalkeeperShootable;
	private JLabel 	visitorForwardName, visitorForwardPosition, visitorForwardSpeed, visitorForwardLos,
					visitorForwardFreshness, visitorForwardMaxFreshness, visitorForwardShootable;
	private JLabel 	visitorDefenderOneName, visitorDefenderOnePosition, visitorDefenderOneSpeed, visitorDefenderOneLos,
					visitorDefenderOneFreshness, visitorDefenderOneMaxFreshness, visitorDefenderOneShootable;
	private JLabel 	visitorDefenderTwoName, visitorDefenderTwoPosition, visitorDefenderTwoSpeed, visitorDefenderTwoLos,
					visitorDefenderTwoFreshness, visitorDefenderTwoMaxFreshness, visitorDefenderTwoShootable;

	private JLabel 	ballPosition, ballSpeed, ballLos;
	
	private JTextArea area;
	
	private JScrollPane scrollPane;
	
	public ApoSoccerDebugFrame(final ApoSoccerGame game) {
		this.setTitle("=== ApoSoccers DebugFrame ===");
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				game.debugChange();
			}
		});

		super.setIconImage( new ApoSoccerImages().getLogo( 16, 16) );
		
		this.setLayout(null);
		JComponent currentComponent	= (JComponent)this.getContentPane();
		currentComponent.setPreferredSize(new Dimension(850, 700));
		currentComponent.setLayout(null);
		this.init();
		this.pack();
	}
	
	public void init() {
		this.area = new JTextArea();
		this.area.setLineWrap(true);
		this.area.setEditable(false);

		this.scrollPane = new JScrollPane(this.area);
		this.scrollPane.setWheelScrollingEnabled(true);
		this.scrollPane.setSize(830, 368);
		this.scrollPane.setLocation(5, 330);
		this.add(this.scrollPane);
		
		JLabel label = new JLabel("Name");
		label.setLocation(3, 30);
		label.setSize(70, 20);
		this.add(label);
		
		label = new JLabel("Position");
		label.setLocation(3, 30 + 20);
		label.setSize(70, 20);
		this.add(label);
		
		label = new JLabel("Speed");
		label.setLocation(3, 30 + 40);
		label.setSize(70, 20);
		this.add(label);
		
		label = new JLabel("Angle");
		label.setLocation(3, 30 + 60);
		label.setSize(70, 20);
		this.add(label);
		
		label = new JLabel("Freshness");
		label.setLocation(3, 30 + 80);
		label.setSize(70, 20);
		this.add(label);
		
		label = new JLabel("MaxFresh");
		label.setLocation(3, 30 + 100);
		label.setSize(70, 20);
		this.add(label);
		
		label = new JLabel("Shootable");
		label.setLocation(3, 30 + 120);
		label.setSize(70, 20);
		this.add(label);
		
		
		label = new JLabel("Name");
		label.setLocation(3, 180);
		label.setSize(70, 20);
		this.add(label);
		
		label = new JLabel("Position");
		label.setLocation(3, 180 + 20);
		label.setSize(70, 20);
		this.add(label);
		
		label = new JLabel("Speed");
		label.setLocation(3, 180 + 40);
		label.setSize(70, 20);
		this.add(label);
		
		label = new JLabel("Angle");
		label.setLocation(3, 180 + 60);
		label.setSize(70, 20);
		this.add(label);
		
		label = new JLabel("Freshness");
		label.setLocation(3, 180 + 80);
		label.setSize(70, 20);
		this.add(label);
		
		label = new JLabel("MaxFresh");
		label.setLocation(3, 180 + 100);
		label.setSize(70, 20);
		this.add(label);
		
		label = new JLabel("Shootable");
		label.setLocation(3, 180 + 120);
		label.setSize(70, 20);
		this.add(label);
		
		
		label = new JLabel("Goalkeeper");
		label.setLocation(80, 5);
		label.setSize(100, 20);
		this.add(label);
		
		label = new JLabel();
		label.setLocation(78, 28);
		label.setSize(140, 144);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(label);
		
		label = new JLabel();
		label.setLocation(78, 28 + 150);
		label.setSize(140, 144);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(label);
		
		label = new JLabel("Forward");
		label.setLocation(230, 5);
		label.setSize(100, 20);
		this.add(label);
		
		label = new JLabel();
		label.setLocation(78 + 150, 28);
		label.setSize(140, 144);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(label);
		
		label = new JLabel();
		label.setLocation(78 + 150, 28 + 150);
		label.setSize(140, 144);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(label);
		
		label = new JLabel("DefenderOne");
		label.setLocation(380, 5);
		label.setSize(100, 20);
		this.add(label);
		
		label = new JLabel();
		label.setLocation(78 + 300, 28);
		label.setSize(140, 144);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(label);
		
		label = new JLabel();
		label.setLocation(78 + 300, 28 + 150);
		label.setSize(140, 144);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(label);
		
		label = new JLabel("DefenderTwo");
		label.setLocation(530, 5);
		label.setSize(100, 20);
		this.add(label);
		
		label = new JLabel();
		label.setLocation(78 + 450, 28);
		label.setSize(140, 144);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(label);
		
		label = new JLabel();
		label.setLocation(78 + 450, 28 + 150);
		label.setSize(140, 144);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(label);

		label = new JLabel("Ball");
		label.setLocation(680, 5);
		label.setSize(100, 20);
		this.add(label);
		
		label = new JLabel();
		label.setLocation(78 + 600, 28);
		label.setSize(140, 144);
		label.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add(label);

		
		
		this.homeGoalkeeperName = new JLabel();
		this.homeGoalkeeperName.setLocation(80, 30);
		this.homeGoalkeeperName.setSize(150, 20);
		this.add(this.homeGoalkeeperName);
		
		this.homeGoalkeeperPosition = new JLabel();
		this.homeGoalkeeperPosition.setLocation(80, 30 + 20);
		this.homeGoalkeeperPosition.setSize(150, 20);
		this.add(this.homeGoalkeeperPosition);
		
		this.homeGoalkeeperSpeed = new JLabel();
		this.homeGoalkeeperSpeed.setLocation(80, 30 + 40);
		this.homeGoalkeeperSpeed.setSize(150, 20);
		this.add(this.homeGoalkeeperSpeed);
		
		this.homeGoalkeeperLos = new JLabel();
		this.homeGoalkeeperLos.setLocation(80, 30 + 60);
		this.homeGoalkeeperLos.setSize(150, 20);
		this.add(this.homeGoalkeeperLos);
		
		this.homeGoalkeeperFreshness = new JLabel();
		this.homeGoalkeeperFreshness.setLocation(80, 30 + 80);
		this.homeGoalkeeperFreshness.setSize(150, 20);
		this.add(this.homeGoalkeeperFreshness);
		
		this.homeGoalkeeperMaxFreshness = new JLabel();
		this.homeGoalkeeperMaxFreshness.setLocation(80, 30 + 100);
		this.homeGoalkeeperMaxFreshness.setSize(150, 20);
		this.add(this.homeGoalkeeperMaxFreshness);
		
		this.homeGoalkeeperShootable = new JLabel();
		this.homeGoalkeeperShootable.setLocation(80, 30 + 120);
		this.homeGoalkeeperShootable.setSize(150, 20);
		this.add(this.homeGoalkeeperShootable);

		this.homeForwardName = new JLabel();
		this.homeForwardName.setLocation(230, 30);
		this.homeForwardName.setSize(150, 20);
		this.add(this.homeForwardName);
		
		this.homeForwardPosition = new JLabel();
		this.homeForwardPosition.setLocation(230, 30 + 20);
		this.homeForwardPosition.setSize(150, 20);
		this.add(this.homeForwardPosition);
		
		this.homeForwardSpeed = new JLabel();
		this.homeForwardSpeed.setLocation(230, 30 + 40);
		this.homeForwardSpeed.setSize(150, 20);
		this.add(this.homeForwardSpeed);
		
		this.homeForwardLos = new JLabel();
		this.homeForwardLos.setLocation(230, 30 + 60);
		this.homeForwardLos.setSize(150, 20);
		this.add(this.homeForwardLos);
		
		this.homeForwardFreshness = new JLabel();
		this.homeForwardFreshness.setLocation(230, 30 + 80);
		this.homeForwardFreshness.setSize(150, 20);
		this.add(this.homeForwardFreshness);
		
		this.homeForwardMaxFreshness = new JLabel();
		this.homeForwardMaxFreshness.setLocation(230, 30 + 100);
		this.homeForwardMaxFreshness.setSize(150, 20);
		this.add(this.homeForwardMaxFreshness);
		
		this.homeForwardShootable = new JLabel();
		this.homeForwardShootable.setLocation(230, 30 + 120);
		this.homeForwardShootable.setSize(150, 20);
		this.add(this.homeForwardShootable);

		this.homeDefenderOneName = new JLabel();
		this.homeDefenderOneName.setLocation(380, 30);
		this.homeDefenderOneName.setSize(150, 20);
		this.add(this.homeDefenderOneName);
		
		this.homeDefenderOnePosition = new JLabel();
		this.homeDefenderOnePosition.setLocation(380, 30 + 20);
		this.homeDefenderOnePosition.setSize(150, 20);
		this.add(this.homeDefenderOnePosition);
		
		this.homeDefenderOneSpeed = new JLabel();
		this.homeDefenderOneSpeed.setLocation(380, 30 + 40);
		this.homeDefenderOneSpeed.setSize(150, 20);
		this.add(this.homeDefenderOneSpeed);
		
		this.homeDefenderOneLos = new JLabel();
		this.homeDefenderOneLos.setLocation(380, 30 + 60);
		this.homeDefenderOneLos.setSize(150, 20);
		this.add(this.homeDefenderOneLos);
		
		this.homeDefenderOneFreshness = new JLabel();
		this.homeDefenderOneFreshness.setLocation(380, 30 + 80);
		this.homeDefenderOneFreshness.setSize(150, 20);
		this.add(this.homeDefenderOneFreshness);
		
		this.homeDefenderOneMaxFreshness = new JLabel();
		this.homeDefenderOneMaxFreshness.setLocation(380, 30 + 100);
		this.homeDefenderOneMaxFreshness.setSize(150, 20);
		this.add(this.homeDefenderOneMaxFreshness);
		
		this.homeDefenderOneShootable = new JLabel();
		this.homeDefenderOneShootable.setLocation(380, 30 + 120);
		this.homeDefenderOneShootable.setSize(150, 20);
		this.add(this.homeDefenderOneShootable);
		
		this.homeDefenderTwoName = new JLabel();
		this.homeDefenderTwoName.setLocation(530, 30);
		this.homeDefenderTwoName.setSize(150, 20);
		this.add(this.homeDefenderTwoName);
		
		this.homeDefenderTwoPosition = new JLabel();
		this.homeDefenderTwoPosition.setLocation(530, 30 + 20);
		this.homeDefenderTwoPosition.setSize(150, 20);
		this.add(this.homeDefenderTwoPosition);
		
		this.homeDefenderTwoSpeed = new JLabel();
		this.homeDefenderTwoSpeed.setLocation(530, 30 + 40);
		this.homeDefenderTwoSpeed.setSize(150, 20);
		this.add(this.homeDefenderTwoSpeed);
		
		this.homeDefenderTwoLos = new JLabel();
		this.homeDefenderTwoLos.setLocation(530, 30 + 60);
		this.homeDefenderTwoLos.setSize(150, 20);
		this.add(this.homeDefenderTwoLos);
		
		this.homeDefenderTwoFreshness = new JLabel();
		this.homeDefenderTwoFreshness.setLocation(530, 30 + 80);
		this.homeDefenderTwoFreshness.setSize(150, 20);
		this.add(this.homeDefenderTwoFreshness);
		
		this.homeDefenderTwoMaxFreshness = new JLabel();
		this.homeDefenderTwoMaxFreshness.setLocation(530, 30 + 100);
		this.homeDefenderTwoMaxFreshness.setSize(150, 20);
		this.add(this.homeDefenderTwoMaxFreshness);
		
		this.homeDefenderTwoShootable = new JLabel();
		this.homeDefenderTwoShootable.setLocation(530, 30 + 120);
		this.homeDefenderTwoShootable.setSize(150, 20);
		this.add(this.homeDefenderTwoShootable);

		this.ballPosition = new JLabel();
		this.ballPosition.setLocation(680, 30 + 20);
		this.ballPosition.setSize(150, 20);
		this.add(this.ballPosition);

		this.ballSpeed = new JLabel();
		this.ballSpeed.setLocation(680, 30 + 40);
		this.ballSpeed.setSize(150, 20);
		this.add(this.ballSpeed);

		this.ballLos = new JLabel();
		this.ballLos.setLocation(680, 30 + 60);
		this.ballLos.setSize(150, 20);
		this.add(this.ballLos);
		
		
		this.visitorGoalkeeperName = new JLabel();
		this.visitorGoalkeeperName.setLocation(80, 180);
		this.visitorGoalkeeperName.setSize(150, 20);
		this.add(this.visitorGoalkeeperName);
		
		this.visitorGoalkeeperPosition = new JLabel();
		this.visitorGoalkeeperPosition.setLocation(80, 180 + 20);
		this.visitorGoalkeeperPosition.setSize(150, 20);
		this.add(this.visitorGoalkeeperPosition);
		
		this.visitorGoalkeeperSpeed = new JLabel();
		this.visitorGoalkeeperSpeed.setLocation(80, 180 + 40);
		this.visitorGoalkeeperSpeed.setSize(150, 20);
		this.add(this.visitorGoalkeeperSpeed);
		
		this.visitorGoalkeeperLos = new JLabel();
		this.visitorGoalkeeperLos.setLocation(80, 180 + 60);
		this.visitorGoalkeeperLos.setSize(150, 20);
		this.add(this.visitorGoalkeeperLos);
		
		this.visitorGoalkeeperFreshness = new JLabel();
		this.visitorGoalkeeperFreshness.setLocation(80, 180 + 80);
		this.visitorGoalkeeperFreshness.setSize(150, 20);
		this.add(this.visitorGoalkeeperFreshness);
		
		this.visitorGoalkeeperMaxFreshness = new JLabel();
		this.visitorGoalkeeperMaxFreshness.setLocation(80, 180 + 100);
		this.visitorGoalkeeperMaxFreshness.setSize(150, 20);
		this.add(this.visitorGoalkeeperMaxFreshness);
		
		this.visitorGoalkeeperShootable = new JLabel();
		this.visitorGoalkeeperShootable.setLocation(80, 180 + 120);
		this.visitorGoalkeeperShootable.setSize(150, 20);
		this.add(this.visitorGoalkeeperShootable);

		this.visitorForwardName = new JLabel();
		this.visitorForwardName.setLocation(230, 180);
		this.visitorForwardName.setSize(150, 20);
		this.add(this.visitorForwardName);
		
		this.visitorForwardPosition = new JLabel();
		this.visitorForwardPosition.setLocation(230, 180 + 20);
		this.visitorForwardPosition.setSize(150, 20);
		this.add(this.visitorForwardPosition);
		
		this.visitorForwardSpeed = new JLabel();
		this.visitorForwardSpeed.setLocation(230, 180 + 40);
		this.visitorForwardSpeed.setSize(150, 20);
		this.add(this.visitorForwardSpeed);
		
		this.visitorForwardLos = new JLabel();
		this.visitorForwardLos.setLocation(230, 180 + 60);
		this.visitorForwardLos.setSize(150, 20);
		this.add(this.visitorForwardLos);
		
		this.visitorForwardFreshness = new JLabel();
		this.visitorForwardFreshness.setLocation(230, 180 + 80);
		this.visitorForwardFreshness.setSize(150, 20);
		this.add(this.visitorForwardFreshness);
		
		this.visitorForwardMaxFreshness = new JLabel();
		this.visitorForwardMaxFreshness.setLocation(230, 180 + 100);
		this.visitorForwardMaxFreshness.setSize(150, 20);
		this.add(this.visitorForwardMaxFreshness);
		
		this.visitorForwardShootable = new JLabel();
		this.visitorForwardShootable.setLocation(230, 180 + 120);
		this.visitorForwardShootable.setSize(150, 20);
		this.add(this.visitorForwardShootable);

		this.visitorDefenderOneName = new JLabel();
		this.visitorDefenderOneName.setLocation(380, 180);
		this.visitorDefenderOneName.setSize(150, 20);
		this.add(this.visitorDefenderOneName);
		
		this.visitorDefenderOnePosition = new JLabel();
		this.visitorDefenderOnePosition.setLocation(380, 180 + 20);
		this.visitorDefenderOnePosition.setSize(150, 20);
		this.add(this.visitorDefenderOnePosition);
		
		this.visitorDefenderOneSpeed = new JLabel();
		this.visitorDefenderOneSpeed.setLocation(380, 180 + 40);
		this.visitorDefenderOneSpeed.setSize(150, 20);
		this.add(this.visitorDefenderOneSpeed);
		
		this.visitorDefenderOneLos = new JLabel();
		this.visitorDefenderOneLos.setLocation(380, 180 + 60);
		this.visitorDefenderOneLos.setSize(150, 20);
		this.add(this.visitorDefenderOneLos);
		
		this.visitorDefenderOneFreshness = new JLabel();
		this.visitorDefenderOneFreshness.setLocation(380, 180 + 80);
		this.visitorDefenderOneFreshness.setSize(150, 20);
		this.add(this.visitorDefenderOneFreshness);
		
		this.visitorDefenderOneMaxFreshness = new JLabel();
		this.visitorDefenderOneMaxFreshness.setLocation(380, 180 + 100);
		this.visitorDefenderOneMaxFreshness.setSize(150, 20);
		this.add(this.visitorDefenderOneMaxFreshness);
		
		this.visitorDefenderOneShootable = new JLabel();
		this.visitorDefenderOneShootable.setLocation(380, 180 + 120);
		this.visitorDefenderOneShootable.setSize(150, 20);
		this.add(this.visitorDefenderOneShootable);
		
		this.visitorDefenderTwoName = new JLabel();
		this.visitorDefenderTwoName.setLocation(530, 180);
		this.visitorDefenderTwoName.setSize(150, 20);
		this.add(this.visitorDefenderTwoName);
		
		this.visitorDefenderTwoPosition = new JLabel();
		this.visitorDefenderTwoPosition.setLocation(530, 180 + 20);
		this.visitorDefenderTwoPosition.setSize(150, 20);
		this.add(this.visitorDefenderTwoPosition);
		
		this.visitorDefenderTwoSpeed = new JLabel();
		this.visitorDefenderTwoSpeed.setLocation(530, 180 + 40);
		this.visitorDefenderTwoSpeed.setSize(150, 20);
		this.add(this.visitorDefenderTwoSpeed);
		
		this.visitorDefenderTwoLos = new JLabel();
		this.visitorDefenderTwoLos.setLocation(530, 180 + 60);
		this.visitorDefenderTwoLos.setSize(150, 20);
		this.add(this.visitorDefenderTwoLos);
		
		this.visitorDefenderTwoFreshness = new JLabel();
		this.visitorDefenderTwoFreshness.setLocation(530, 180 + 80);
		this.visitorDefenderTwoFreshness.setSize(150, 20);
		this.add(this.visitorDefenderTwoFreshness);
		
		this.visitorDefenderTwoMaxFreshness = new JLabel();
		this.visitorDefenderTwoMaxFreshness.setLocation(530, 180 + 100);
		this.visitorDefenderTwoMaxFreshness.setSize(150, 20);
		this.add(this.visitorDefenderTwoMaxFreshness);
		
		this.visitorDefenderTwoShootable = new JLabel();
		this.visitorDefenderTwoShootable.setLocation(530, 180 + 120);
		this.visitorDefenderTwoShootable.setSize(150, 20);
		this.add(this.visitorDefenderTwoShootable);
	}
	
	public void setValues(ApoTeam homeTeam, ApoTeam visitorTeam, ApoBall ball) {
		ApoPlayer player = homeTeam.getPlayer(ApoSoccerConstants.GOALKEEPER);
		this.homeGoalkeeperName.setText(player.getName());
		this.homeGoalkeeperPosition.setText("x: "+(int)player.getX()+" y: "+(int)player.getY());
		this.homeGoalkeeperSpeed.setText(""+player.getSpeed());
		this.homeGoalkeeperLos.setText(""+player.getLineOfSight());
		this.homeGoalkeeperFreshness.setText(""+player.getFreshness());
		this.homeGoalkeeperMaxFreshness.setText(""+player.getMaxFreshness());
		this.homeGoalkeeperShootable.setText(""+player.isBCanShoot());

		player = homeTeam.getPlayer(ApoSoccerConstants.FORWARD);
		this.homeForwardName.setText(player.getName());
		this.homeForwardPosition.setText("x: "+(int)player.getX()+" y: "+(int)player.getY());
		this.homeForwardSpeed.setText(""+player.getSpeed());
		this.homeForwardLos.setText(""+player.getLineOfSight());
		this.homeForwardFreshness.setText(""+player.getFreshness());
		this.homeForwardMaxFreshness.setText(""+player.getMaxFreshness());
		this.homeForwardShootable.setText(""+player.isBCanShoot());

		player = homeTeam.getPlayer(ApoSoccerConstants.DEFENDER_ONE);
		this.homeDefenderOneName.setText(player.getName());
		this.homeDefenderOnePosition.setText("x: "+(int)player.getX()+" y: "+(int)player.getY());
		this.homeDefenderOneSpeed.setText(""+player.getSpeed());
		this.homeDefenderOneLos.setText(""+player.getLineOfSight());
		this.homeDefenderOneFreshness.setText(""+player.getFreshness());
		this.homeDefenderOneMaxFreshness.setText(""+player.getMaxFreshness());
		this.homeDefenderOneShootable.setText(""+player.isBCanShoot());

		player = homeTeam.getPlayer(ApoSoccerConstants.DEFENDER_TWO);
		this.homeDefenderTwoName.setText(player.getName());
		this.homeDefenderTwoPosition.setText("x: "+(int)player.getX()+" y: "+(int)player.getY());
		this.homeDefenderTwoSpeed.setText(""+player.getSpeed());
		this.homeDefenderTwoLos.setText(""+player.getLineOfSight());
		this.homeDefenderTwoFreshness.setText(""+player.getFreshness());
		this.homeDefenderTwoMaxFreshness.setText(""+player.getMaxFreshness());
		this.homeDefenderTwoShootable.setText(""+player.isBCanShoot());

		player = visitorTeam.getPlayer(ApoSoccerConstants.GOALKEEPER);
		this.visitorGoalkeeperName.setText(player.getName());
		this.visitorGoalkeeperPosition.setText("x: "+(int)player.getX()+" y: "+(int)player.getY());
		this.visitorGoalkeeperSpeed.setText(""+player.getSpeed());
		this.visitorGoalkeeperLos.setText(""+player.getLineOfSight());
		this.visitorGoalkeeperFreshness.setText(""+player.getFreshness());
		this.visitorGoalkeeperMaxFreshness.setText(""+player.getMaxFreshness());
		this.visitorGoalkeeperShootable.setText(""+player.isBCanShoot());

		player = visitorTeam.getPlayer(ApoSoccerConstants.FORWARD);
		this.visitorForwardName.setText(player.getName());
		this.visitorForwardPosition.setText("x: "+(int)player.getX()+" y: "+(int)player.getY());
		this.visitorForwardSpeed.setText(""+player.getSpeed());
		this.visitorForwardLos.setText(""+player.getLineOfSight());
		this.visitorForwardFreshness.setText(""+player.getFreshness());
		this.visitorForwardMaxFreshness.setText(""+player.getMaxFreshness());
		this.visitorForwardShootable.setText(""+player.isBCanShoot());

		player = visitorTeam.getPlayer(ApoSoccerConstants.DEFENDER_ONE);
		this.visitorDefenderOneName.setText(player.getName());
		this.visitorDefenderOnePosition.setText("x: "+(int)player.getX()+" y: "+(int)player.getY());
		this.visitorDefenderOneSpeed.setText(""+player.getSpeed());
		this.visitorDefenderOneLos.setText(""+player.getLineOfSight());
		this.visitorDefenderOneFreshness.setText(""+player.getFreshness());
		this.visitorDefenderOneMaxFreshness.setText(""+player.getMaxFreshness());
		this.visitorDefenderOneShootable.setText(""+player.isBCanShoot());

		player = visitorTeam.getPlayer(ApoSoccerConstants.DEFENDER_TWO);
		this.visitorDefenderTwoName.setText(player.getName());
		this.visitorDefenderTwoPosition.setText("x: "+(int)player.getX()+" y: "+(int)player.getY());
		this.visitorDefenderTwoSpeed.setText(""+player.getSpeed());
		this.visitorDefenderTwoLos.setText(""+player.getLineOfSight());
		this.visitorDefenderTwoFreshness.setText(""+player.getFreshness());
		this.visitorDefenderTwoMaxFreshness.setText(""+player.getMaxFreshness());
		this.visitorDefenderTwoShootable.setText(""+player.isBCanShoot());
		
		this.ballPosition.setText("x: "+(int)ball.getX()+" y: "+(int)ball.getY());
		this.ballSpeed.setText(""+ball.getSpeed());
		this.ballLos.setText(""+ball.getLineOfSight());
	}
	
	public void addString(String addString) {
		this.area.append(addString + "\n");
		if (this.scrollPane != null) {
			JScrollBar scrollbar = this.scrollPane.getVerticalScrollBar();
			scrollbar.setValue(scrollbar.getMaximum());
		}
	}
	
	public void clear() {
		this.area.setText("");
		if (this.scrollPane != null) {
			JScrollBar scrollbar = this.scrollPane.getVerticalScrollBar();
			scrollbar.setValue(scrollbar.getMaximum());
		}
	}
	
}
