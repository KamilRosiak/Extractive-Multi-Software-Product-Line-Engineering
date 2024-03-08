package com.apogames.mytreasure.entity;

import java.util.ArrayList;

import android.util.Log;

import com.apogames.mytreasure.game.MyTreasureEditor;

public class MyTreasureSolve {

	private final int MAX = 17;
	private final int NOSOLUTIONCHECK = 25;
	
	private ArrayList<String> solutions, noSolutions;
	
	private final MyTreasureEditor editor;
	
	public MyTreasureSolve(final MyTreasureEditor editor) {
		this.editor = editor;
		
		this.solutions = new ArrayList<String>();
		this.noSolutions = new ArrayList<String>();
	}

	public String solveLevel(String level) {
		int[][] levelArray = new int[Integer.valueOf(level.substring(2, 4))][Integer.valueOf(level.substring(0, 2))];
		for (int y = 0; y < levelArray.length; y++) {
			for (int x = 0; x < levelArray[0].length; x++) {
				levelArray[y][x] = Integer.valueOf(level.substring(4 + x + y * levelArray[0].length, 4 + x + y * levelArray[0].length + 1));
			}
		}
		return this.solveLevel(levelArray);
	}
	
	public String solveLevel(final int[][] level) {
		String solveString = "no solution in max "+(this.MAX-1)+" steps";
		
		this.rotateLevel(level, 0);
		
		ArrayList<String> levelStrings = new ArrayList<String>();
		this.noSolutions.clear();
		
		int count = 1;
		boolean bBreak = false;
		while ((!bBreak) && (count < this.MAX)) {
			this.getSolutionsForGivenSteps(count);
			
			this.editor.setTestString("Test "+count+" steps");
			
//			System.out.println(count+" "+this.solutions.size()+" "+this.solutions.get(0));
			
			for (int i = 0; i < this.solutions.size(); i++) {
				levelStrings.clear();
				boolean bSolveCheck = true;
				if (count >= this.NOSOLUTIONCHECK) {
					for (int j = 0; j < this.noSolutions.size(); j++) {
						if (this.noSolutions.get(j).equals(this.solutions.get(i).substring(0, this.noSolutions.get(j).length()))) {
							Log.d("solutionCheck",this.noSolutions.size()+" "+this.noSolutions.get(j));
							bSolveCheck = false;
							break;
						}
					}
				}
				if ((bSolveCheck) && (i < this.solutions.size())) {
					if (solveLevel(this.getCopy(level), this.solutions.get(i), levelStrings)) {
						bBreak = true;
						return this.solutions.get(i);
					}
				}
			}
			
			count += 1;
		}
		
		return solveString;
	}
	
	public final int[][] getCopy(final int[][] original) {
		int copy[][] = new int[original.length][original[0].length];
		for (int y = 0; y < original.length; y++) {
			for (int x = 0; x < original[0].length; x++) {
				copy[y][x] = original[y][x];
			}
		}
		return copy;
	}
	
	private void getSolutionsForGivenSteps(final int steps) {
		this.solutions.clear();
		
		this.getSolutionStringForGivenSteps(steps, true, "");
		this.getSolutionStringForGivenSteps(steps, false, "");
	}
	
	private void getSolutionStringForGivenSteps(int steps, boolean bLeft, String curString) {
		if (bLeft) {
			curString += "l";
		} else {
			curString += "r";
		}
		if (steps == 1) {
			this.solutions.add(curString);
		} else {
			this.getSolutionStringForGivenSteps(steps - 1, true, curString);
			this.getSolutionStringForGivenSteps(steps - 1, false, curString);
		}
	}
	
	public boolean solveLevel(final int[][] level, String solveLevel, final ArrayList<String> levelStrings) {
		int gravity = 0;
		
		if (solveLevel.length() > this.NOSOLUTIONCHECK) {
			levelStrings.add(this.getLevelString(level));
		}
		
		for (int i = 0; i < solveLevel.length(); i++) {
			if (solveLevel.substring(i, i + 1).equals("l")) {
				gravity = this.getGravity(gravity, -1);
			} else {
				gravity = this.getGravity(gravity, +1);
			}
			rotateLevel(level, gravity);
			
			if (solveLevel.length() > this.NOSOLUTIONCHECK) {
				String levelString = this.getLevelString(level);
				for (int j = 0; j < levelStrings.size(); j++) {
					if (levelStrings.get(j).equals(levelString)) {
						this.noSolutions.add(solveLevel.substring(0, i + 1));
						return false;
					}
				}
				
				if (solveLevel.length() > 10) {
					levelStrings.add(this.getLevelString(level));
				}
			}
		}
		
		return this.isWin(level);
	}
	
	public String getLevelString(final int[][] level) {
		String levelString = "";
		String width = String.valueOf(level[0].length);
		if (width.length() < 2) {
			width = "0" + width;
		}
		String height = String.valueOf(level.length);
		if (height.length() < 2) {
			height = "0" + height;
		}
		levelString = width + height;
		for (int y = 0; y < level.length; y += 1) {
			for (int x = 0; x < level[0].length; x += 1) {
				levelString += String.valueOf(level[y][x]);
			}
		}
		return levelString;
	}
	
	public void drawLevel(int[][] level) {
		for (int y = 0; y < level.length; y++) {
			for (int x = 0; x < level[0].length; x++) {
				System.out.print(level[y][x]);
			}
			System.out.println();
		}
		System.out.println();
	}

	private int getGravity(int gravity, int add) {
		gravity = gravity + add;
		if (gravity < 0) {
			gravity = 3;
		}
		if (gravity > 3) {
			gravity = 0;
		}
		return gravity;
	}
	
	public void rotateLevel(final int[][] level, int gravity) {
		int startY = level.length - 1;
		int addY = -1;
		int startX = 0;
		int addX = 1;
		int addFall = -1;
		if (gravity == 2) {
			startY = 0;
			addY = 1;
			addFall = 1;
		}
		if (gravity == 1) {
			addX = -1;
			startX = level[0].length - 1;
		}
		if (gravity == 3) {
			addFall = 1;
		}

		int add = 0;
		int addPlayer = 0;
		if (gravity % 2 == 0) {
			for (int x = startX; x >= 0 && x <= level[0].length - 1; x += addX) {
				add = 0;
				addPlayer = 0;
				for (int y = startY; y >= -addY && y <= level.length - 1 - addY; y += addY) {
					if (level[y][x] == 1) {
						add -= 1 * addFall;
					} else if (level[y][x] == 3) {
						addPlayer -= 1 * addFall;
					} else if (level[y][x] == 2) {
						if ((level[y + add + addPlayer][x] == 1) || (level[y + add + addPlayer][x] == 3)) {
							level[y][x] = 1;
							level[y + add + addPlayer][x] = 2;
							if (level[y + add + addPlayer][x] == 3) {
								level[y + add + addPlayer][x] = 0;
							}
						}
						if (addPlayer != 0) {
							for (int yy = startY; yy >= 0 && yy <= level.length - 1; yy += addY) {
								if (level[yy][x] == 3) {
									level[yy][x] = 1;
								}
							}
						}
					} else if (level[y][x] == 4) {
						if (level[y + add][x] == 1) {
							level[y][x] = 1;
							level[y + add][x] = 4;
							addPlayer = 0;
						}
					}
					
					if (level[y][x] != 1) {	
						add = 0;
					}
					if ((level[y][x] != 3) && (level[y][x] != 1)) {
						addPlayer = 0;
					}
					
				}
			}

		} else {
			for (int y = startY; y >= 0 && y <= level.length - 1; y += addY) {
				add = 0;
				addPlayer = 0;
				for (int x = startX; x >= -addX && x <= level[0].length - 1 - addX; x += addX) {
					if (level[y][x] == 1) {
						add -= 1 * addFall;
					}
					if (level[y][x] == 3) {
						addPlayer -= 1 * addFall;
					}
					if (level[y][x] == 2) {
						if (((level[y][x + add + addPlayer] == 1) || (level[y][x + add + addPlayer] == 3))
								&& (level[y][x + add + addPlayer] < 4)) {
							level[y][x] = 1;
							level[y][x + add + addPlayer] = 2;
							if (level[y][x + add + addPlayer] == 3) {
								level[y][x + add + addPlayer] = 0;
							}
						}
						if (addPlayer != 0) {
							for (int xx = startX; xx >= 0 && xx <= level[0].length - 1; xx += addX) {
								if (level[y][xx] == 3) {
									level[y][xx] = 0;
								}
							}
						}
					}
					if (level[y][x] == 4) {
						if (level[y][x + add] == 1) {
							level[y][x] = 1;
							level[y][x + add] = 4;
							addPlayer = 0;
						}
					}
					if (level[y][x] != 1) {	
						add = 0;
					}
					if ((level[y][x] != 3) && (level[y][x] != 1)) {
						addPlayer = 0;
					}
				}
			}
		}

	}

	public boolean isWin(final int[][] level) {
		boolean bWin = true;
		for (int y = 0; y < level.length; y += 1) {
			for (int x = 0; x < level[0].length; x += 1) {
				if (level[y][x] == 3) {
					bWin = false;
					break;
				}
			}
		}
		return bWin;
	}

}
