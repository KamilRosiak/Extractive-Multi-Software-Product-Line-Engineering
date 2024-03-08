package apoSlitherLink.level;

import java.util.ArrayList;

public class ApoSlitherLinkLevels {

	private ArrayList<ApoSlitherLinkLevelsHelp> easyLevels;
	
	private ArrayList<ApoSlitherLinkLevelsHelp> mediumLevels;
	
	private ArrayList<ApoSlitherLinkLevelsHelp> hardLevels;
	
	private ArrayList<ApoSlitherLinkLevelsHelp> customLevels;
	
	public ApoSlitherLinkLevels() {
		this.setEasyLevels();
		this.setMediumLevels();
		this.setHardLevels();
		this.customLevels = new ArrayList<ApoSlitherLinkLevelsHelp>();
	}
	
	private void setEasyLevels() {
		this.easyLevels = new ArrayList<ApoSlitherLinkLevelsHelp>();
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0404  32 3     2 0 0"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("04040  01     3  0  "));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0404 20       2202  "));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0404  311   3  312  "));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0404 2 3    33  2  3"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("040411     3 2 3 2 2"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("04042123   2 13  1  "));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("04041  0    1 21 23 "));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0404 23  112 1  13 1"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0404 2   3    2 3  2"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0505  0    3  1  2  23       "));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0505 232    0 21  21 1 1  110"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0505 3 3   0 2    1 2 2  23 3"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("05051323     2  2 30      131"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0505 11 1 3  2 2   23     321"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("05051 211  2    02      112 1"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0505  22 3 21         3102 23"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0505 2   23      2 22  3 3  3"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0505 1  01   11 3  22 2  223 "));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("05051   1 2    2 2  23   3  1"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0505 1 13  2   3      212  31"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0606 2    3 22   222 2 222  2   22 222 2"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0606  11 32 3  23  1   11      10322 2  "));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0606 1   131 113 11  2 222       113 131"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0606 1    10 2 3 1 112  1  2 2 112 3 2  "));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("06063233 3  1    2  1      32 3      3 3"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("06062      2 3      2   0 0  3  2  1 3  "));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("060622 223 3 2 2    3  22  2   23 1 22 3"));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0606 0  3    2  2   21   12202  23 32   "));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0606232110        2 1 1 3 2 2  1 33 33  "));
		this.easyLevels.add(new ApoSlitherLinkLevelsHelp("0606 3 3   1 2     0 302   1  21 13   2 "));
	}
	
	public final ArrayList<ApoSlitherLinkLevelsHelp> getEasyLevels() {
		return this.easyLevels;
	}
	
	public final ApoSlitherLinkLevelsHelp getEasyLevel(int level) {
		if ((level >= 0) && (level < this.easyLevels.size())) {
			return this.easyLevels.get(level);
		}
		return null;
	}
	
	public final int getEasyLevelsSolved() {
		int solved = 0;
		for (int i = 0; i < this.easyLevels.size(); i++) {
			if (this.easyLevels.get(i).isBSolved()) {
				solved += 1;
			}
		}
		return solved;
	}

	private void setMediumLevels() {
		this.mediumLevels = new ArrayList<ApoSlitherLinkLevelsHelp>();
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("070723  213   1  23  12 1 2 2         13 011  1  2 3 "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("0707   02      3 3 312  2    3 22     33    3  320   "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("0707    22 3 3  21  3        223 3 3      3  1 3  23 "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("07070  2     1123 2 12   2 12      2 2 32   102      "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("0707      1 200      1 1 2   12 03 32 3  1  221   1  "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("070722  02   0    1  11  1        3 2223    222 21 3 "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("070713  3 0 1    2   1   221122  1 3    2  1   2  322"));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("0707 2    0 22     20      001 1  2 32  222  1 2  2  "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("07071132        33  3 01212        212    1    2   22"));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("0707 1 1  11   11   1 1 1 1 11   1 1 1     1 11111 1 "));
		
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("080812  2    3 22 32   232  3  01     21    31    12    1 3     13  "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("08080 2212      22 1 0    2 2    2  2 2  221 32    33 1  2 2222  1  "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("08080    1 2   2 2 2 12     2     12131 2 12   3131 2     2 22 113  "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("0808 3   3 0 12   312      10  212    1      0 1  3 221 22 0       1"));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("08080 2  3  2      2   2 0 3  12 2 202 1   1   32  32    2  22  32  "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("0808   2 2 2  2222      2222    22 22 122 12 2  222  222 2 222222222"));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("080812 2  21   21  322     3  3 22  3      22   2222  221 121  32 1 "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("0808        02  1213      1  011 0   2   1   3   2223        3202 23"));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("080823  10         32 1 1  2 11   2 11  13 2 2   2 2 1 2 22223 11 2 "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("08081   111 1 1    1 1    1     11 11 1111   11     1 111111 1 11  1"));
		
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("0909 32       1  20 31  2  0   0 22  3 31 2 1   2   3     3 1 11112 1  22  21 3  0 2 "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("090912 3    1  3  23 1    11   1 12 2 22       1 23 21   2   2      3 112 3232223 2 3"));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("09092 22     33 11  3  22   2 2        1  11231 02 1  2  2   1   2    2 2   1  23    "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("0909 11 3 1   3  2  22    0 20   12 223 2 1      222     23   23     1 2 21 1 2  22 3"));
		
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("10103   02   2  31  21   21    22  3  23  0 3  3  0  31  2  2  3 2  33  3  30    12   22  31  1   31   0"));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("1010          3333  2122   2  1   0  2  2 1  3 2  1  32  2  2 2  3 2  3  1   2  0   1303  3313          "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("1010 22   12      1     23  1  22  3 3 33  2   0     02     3   1  03 3 0  33  1  12     1      32   23 "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("1010      132 3  213 2  303    0  3       1  2 1  3 3  1 2  3 1  3       3  2    212  2 333  2 201      "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("1010   1  3 2 02  33  0   0    1  0  3 13  3 1 0    1  1    0 2 3  12 3  3  2    2   3  11  21 3 1  0   "));
		this.mediumLevels.add(new ApoSlitherLinkLevelsHelp("1010   0223      3  2    30    23  3  03  0 2  3  0  12  1  2  1 3  33  3  30    30    1  0      2212   "));

	}
	
	public final ArrayList<ApoSlitherLinkLevelsHelp> getMediumLevels() {
		return this.mediumLevels;
	}
	
	public final ApoSlitherLinkLevelsHelp getMediumLevel(int level) {
		if ((level >= 0) && (level < this.mediumLevels.size())) {
			return this.mediumLevels.get(level);
		}
		return null;
	}
	
	public final int getMediumLevelsSolved() {
		int solved = 0;
		for (int i = 0; i < this.mediumLevels.size(); i++) {
			if (this.mediumLevels.get(i).isBSolved()) {
				solved += 1;
			}
		}
		return solved;
	}
	
	private void setHardLevels() {
		this.hardLevels = new ArrayList<ApoSlitherLinkLevelsHelp>();
		this.hardLevels.add(new ApoSlitherLinkLevelsHelp("1212 3331 31 2 1 0   2   0 23      0   33 0 3 3  2321       11     11      22      33     03       2202  20202013   2      22 0   2   2 1 2 12 2132 "));
		this.hardLevels.add(new ApoSlitherLinkLevelsHelp("1212 1 3 33233 1  2        13  2  2  3  3    3   32 1  3 2  22  2 2                  1 3  32  3 3  1 32   0    0  2  3  3  21        3  2 31232 2 1 "));
		this.hardLevels.add(new ApoSlitherLinkLevelsHelp("1410 33 3 201 233  0 1     1  0 1 2 3 01   32   31 1   1  1  2  3  3   1 2 210  0 1 3 333   3 2   3     1  3     0 1 1 3 1  3 3 3 31   23 1    3"));
		this.hardLevels.add(new ApoSlitherLinkLevelsHelp("141013  3 2 3 1  2    1 3  11 13 3   1 13    1  3 1 2  12 3 23   1   3  12    3 211 2 1 3 23  3 3 2 3  0   3 0  1  3    2 1122 3 3132   1   2 1 "));
		this.hardLevels.add(new ApoSlitherLinkLevelsHelp("14102 3  3 3 33 3  3 1  1 2 2  3              1 3 1032 2  1  31       21    2 3 21  3 333  3 0 3  3  2               3 11   3 1 3 2 3  32  3 3 2"));
		this.hardLevels.add(new ApoSlitherLinkLevelsHelp("14101 3 0 1 1 2 11    2   0 1   2  3  3  1  2 2 1    02 313 22331  1    2 1  1 2  1 3  221 2   1  0  2    2  2    1    3    2 11 133  123 11 1 1"));
		this.hardLevels.add(new ApoSlitherLinkLevelsHelp("14102  3  23 1 1 1 3  1 1  1  2        3  1      3 23 1 31023 2     13  1  0  2 2 2  32 3  1   12   1   3 122   210 20  0   1 3  2   21 3 132  2"));
		this.hardLevels.add(new ApoSlitherLinkLevelsHelp("1410 20 1   3 31 02   3 22  13      0 1 1      11     3  022  0 13  21    3  1 122  0  1  3    32  1  21 2 22 11  13    21  31 0 2232  1 2   3  "));
		this.hardLevels.add(new ApoSlitherLinkLevelsHelp("1410 3   1  1 31 02 110  1   2  31     1  111 1  3331 20     12  1    3 23       2 2 1 32 0121 1 12 133 13 3 3   2          1 3 1 13 012  23 0  "));
		this.hardLevels.add(new ApoSlitherLinkLevelsHelp("1410  3 10 3 1  0    1          331 3 1 0101 2  0 1  1    3  1   2 211   1 1 21 3  0   33211    3 13 2      2 1 1 1    11  11102  3 1         1 "));
		this.hardLevels.add(new ApoSlitherLinkLevelsHelp("14103 31  1 2 3  3 1  3  3 3       13 3    10330    1   3      2  2  211 131 23   1   1      1 3 2 3 1  201 3  33  13       2      312  33 12 21"));
		this.hardLevels.add(new ApoSlitherLinkLevelsHelp("141410  3  2 2   0    0   3    221  3    12    12    232 33           3  13    3 0     2 0 3   3  2 2221 1  2   0 3 3     2 3    22  3           32 331    20    21    2  320    0   3    1   0 3  3  31"));
		this.hardLevels.add(new ApoSlitherLinkLevelsHelp("14143  2 1 12 2  3233     1 0     1 1 1     0 22 3 2   31  312 3 3 1   3 2       21    3  1 2  2  3  33  3  3  3 3  1    23       2 0   2 1 2 222  22   3 2 33 3     0 3 2     2 3     2333  3 13 1 2  2"));
		this.hardLevels.add(new ApoSlitherLinkLevelsHelp("16120  2 2 311 3 13 1  0 1         3 1  3 1 33  3 1 2 1 1  1   0    3   3  11 3    3 1  13   23 32  3  1    3   3 32 13 31   1  1     3   3 3  3 1 3 1 21  2 2   3  3 3     3 3 1  1    333  12 3  3"));
		this.hardLevels.add(new ApoSlitherLinkLevelsHelp("1515 2  3  2   1    1 3 0 311   30  1   1   3 1  3  121  0  2 1   1   1  0 2  2 3  0  2   0 3  1 0   0    0  13   03 32   21  2    2   0 2  3 2   1  0  2 2  0 0  1   3   2 2  0  112  1  1 2   3   1  33   323 1 1 2    0   2  0  2 "));
	}
	
	public final ArrayList<ApoSlitherLinkLevelsHelp> getHardLevels() {
		return this.hardLevels;
	}
	
	public final ApoSlitherLinkLevelsHelp getHardLevel(int level) {
		if ((level >= 0) && (level < this.hardLevels.size())) {
			return this.hardLevels.get(level);
		}
		return null;
	}
	
	public final int getHardLevelsSolved() {
		int solved = 0;
		for (int i = 0; i < this.hardLevels.size(); i++) {
			if (this.hardLevels.get(i).isBSolved()) {
				solved += 1;
			}
		}
		return solved;
	}
	
	public void addCustomLevel(String levelString) {
		for (int i = 0; i < this.customLevels.size(); i++) {
			if (this.customLevels.get(i).getLevelString().equals(levelString)) {
				return;
			}
		}
		this.customLevels.add(new ApoSlitherLinkLevelsHelp(levelString));
	}
	
	public final ArrayList<ApoSlitherLinkLevelsHelp> getCustomLevels() {
		return this.customLevels;
	}
	
	public final ApoSlitherLinkLevelsHelp getCustomLevel(int level) {
		if ((level >= 0) && (level < this.customLevels.size())) {
			return this.customLevels.get(level);
		}
		return null;
	}
	
	public final int getCustomLevelsSolved() {
		int solved = 0;
		for (int i = 0; i < this.customLevels.size(); i++) {
			if (this.customLevels.get(i).isBSolved()) {
				solved += 1;
			}
		}
		return solved;
	}
}