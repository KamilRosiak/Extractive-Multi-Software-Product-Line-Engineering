package apoSkunkman.level.generators;

import java.util.Random;

/**
 * Klasse zur Erstellung von DeadEnd-Levels
 * 
 * @author Enrico Ebert
 */
public class ApoSkunkmanLevelDeadEnd {
	public static void calc(boolean[][] level, Random random) {
		/*
		 * Beim Lesen der Kommentare den Editor mit Standard-Level aufmachen.
		 * Level muss mindestens 15x15 sein.
		 */
		
		// Standard-Level als Grundlage erstellen
		for (int y = 0; y < level.length; y += 2) {
			for (int x = 0; x < level[0].length; x += 2) {
				level[y][x] = false;
			}
		}
		
		/*
		 * Level besteht aus 6x6 Steinen, bzw. 3x3 4er-Blöcken
		 * oy -> Durchlauf in y-Richtung
		 * ox -> Durchlauf in x-Richtung
		 * oz -> Diagonal-Veschiebung nach rechts unten
		 */
		for (int oy = 0; oy < 3; oy++) {
			for (int ox = 0; ox < 3; ox++) {
				for (int oz = 0; oz < 2; oz++) {	// oz < 1 um Verschiebung abzuschalten und gucken was passiert
					if (((oy > 1) || (ox > 1)) && (oz > 0)) {	// nur die linken, oberen 4 Blöcke verschieben
						continue;								// untere und rechte Blockreihe würde das Spielfeld verlassen
					}
					
					/*
					 * anz - Anzahl zu setzende Steine im Block
					 * 		420er-Version (311er produziert mehr Steine)
					 * 		mindestens 0 Steine
					 * 		normaler Block max 3 Steine
					 * 		Verschiebung min 0, max 1 Steine (Anm. bei 2 Steinen entstehen abgeschlossene Bereiche)
					 */
					int anz = random.nextInt((oz == 0) ? 4 : 2) + 0;	// wenig Steine
//					int anz = random.nextInt((oz == 0) ? 3 : 1) + 1;	// mehr Steine
					
					int o = random.nextInt(4);		// Rotations-Offset, bei welcher Seite des Blocks angefangen werden soll
					
					for (int n = 0; n < anz; n++) {
						switch ((((n + o) % ((oz == 0) ? 4 : 2)) + 					// Startposition bei normalem Block
								((oz == 1) ? (3	+ ox * 3 + oy * 1 + 2 * ox * oy)	// Startposition bei Verschiebungs-Block
								: 0)) % 4) {										// im Switch bleiben ;)
						case 0:
							level[oz * 2 + oy * 4 + 3][oz * 2 + ox * 4 + 2] = false;	// links
							break;
						case 1:
							level[oz * 2 + oy * 4 + 4][oz * 2 + ox * 4 + 3] = false;	// unten
							break;
						case 2:
							level[oz * 2 + oy * 4 + 3][oz * 2 + ox * 4 + 4] = false;	// rechts
							break;
						case 3:
							level[oz * 2 + oy * 4 + 2][oz * 2 + ox * 4 + 3] = false;	// oben
							break;
						}
					}
				}
			}
		}
	}
}
