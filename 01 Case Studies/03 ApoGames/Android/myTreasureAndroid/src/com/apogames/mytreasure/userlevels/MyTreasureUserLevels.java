/*
 * Copyright (c) 2005-2013 Dirk Aporius <dirk.aporius@gmail.com>
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.apogames.mytreasure.userlevels;

import java.util.ArrayList;

import com.apogames.mytreasure.game.MyTreasurePanel;

public class MyTreasureUserLevels {
	
	private ArrayList<Integer> sortByUpload;
	
	private MyTreasureUserlevelsLoad userlevels;
	
	protected final MyTreasurePanel game;
	
	public MyTreasureUserLevels(final MyTreasurePanel game) {
		this.game = game;
		this.sortByUpload = new ArrayList<Integer>();
	}
	
	public void loadUserlevels() {
		try {
			this.userlevels = MyTreasureUserlevelsLoad.getInstance();
			this.userlevels.load();
			
			if (this.userlevels.getLevels().size() > 0) {
				this.sortByUpload();
				
				MyTreasureLevels.editorLevels = this.getAllLevelsSorted();
				
				this.game.setUserlevelsVisible();
			}
		} catch (Exception ex) {
			this.sortByUpload = new ArrayList<Integer>();
		}
	}
	
	public int getMaxLevel() {
		return this.userlevels.getLevels().size();
	}
	
	private String[] getAllLevelsSorted() {
		if (this.userlevels.getLevels().size() <= 0) {
			return null;
		}
		int size = this.sortByUpload.size();
		String[] levels = new String[size];
		for (int level = 0; level < levels.length; level++) {
			String curLevel = this.userlevels.getLevels().get(this.sortByUpload.get(level));
			levels[level] = curLevel;
		}
		return levels;
	}
	
	private void sortByUpload() {
		this.sortByUpload.clear();
		for (int i = 0; i < this.userlevels.getLevels().size(); i++) {
			if (!MyTreasureLevels.isIn(this.userlevels.getLevels().get(i))) {
				float time = this.userlevels.getTimes().get(i);
				boolean bAdd = false;
				for (int k = 0; k < this.sortByUpload.size(); k++) {
					float sortTime = this.userlevels.getTimes().get(this.sortByUpload.get(k));
					if (time > sortTime) {
						this.sortByUpload.add(k, i);
						bAdd = true;
						break;
					}
				}
				if (!bAdd) {
					this.sortByUpload.add(i);
				}
			}
		}
	}

	public ArrayList<Integer> getSortByUpload() {
		return this.sortByUpload;
	}
	
	public boolean isIn(final String level) {
		for (int i = 0; i < MyTreasureLevels.MAX_LEVELS; i++) {
			if (MyTreasureLevels.isInWithoutSteps(level)) {
				return false;
			}
		}
		for (int i = 0; i < this.userlevels.getLevels().size(); i++) {
			if (this.userlevels.getLevels().get(i).substring(0, this.userlevels.getLevels().get(i).length() - 4).equals(level.substring(0, level.length() - 4))) {
				return false;
			}
		}
		return true;
	}
	
	public boolean addLevel(String level) {
		if (this.isIn(level)) {
			return MyTreasureUserlevelsLoad.getInstance().save(level);
		}
		return false;
	}
	
	public String getStatistics(int level) {		
		int[] values = new int[4];
		values[0] = this.userlevels.getFun().get(this.sortByUpload.get(level));
		values[1] = this.userlevels.getCreative().get(this.sortByUpload.get(level));
		values[2] = this.userlevels.getEasy().get(this.sortByUpload.get(level));
		values[3] = this.userlevels.getHard().get(this.sortByUpload.get(level));
		
		int max = this.getHighest(-1, values);
		int maxTwo = this.getHighest(max, values);
		String result = "";
		for (int i = 0; i < values.length; i++) {
			if ((i != max) && (i != maxTwo)) {
				result += "0";
			} else {
				if (values[i] > 0) {
					result += "1";
				} else {
					result += "0";
				}
			}
		}

		return result;
	}
	
	private int getHighest(int not, int[] values) {
		int curValue = 0;
		if (not == 0) {
			curValue = 1;
		}
		int max = values[curValue];
		for (int i = 0; i < values.length; i++) {
			if ((max < values[i]) && (i != not)) {
				max = values[i];
				curValue = i;
			}
		}
		return curValue;
	}
	
	public int getID(int level) {
		return this.userlevels.getId().get(this.sortByUpload.get(level));
	}
}