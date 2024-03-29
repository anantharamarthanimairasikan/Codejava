package corejava1;

public class CoreJava2ndExercise {
	public static int nCapsules = 0;
	public double volume;
	public String screenText;

	public CoreJava2ndExercise(double volume, String s) {
		this.volume = volume;
		screenText = s;
		nCapsules++;
	}

	public static void main(String[] args) {
		int[] nInPack = { 5, 10, 10 };
		CoreJava2ndExercise[][] pack = new CoreJava2ndExercise[3][];

		for (int i = 0; i < pack.length; i++) {
			pack[i] = new CoreJava2ndExercise[nInPack[i]];
			for (int j = 0; j < pack[i].length; j++) {
				pack[i][j] = new CoreJava2ndExercise(0.5, "Formular" + i + j);
			}
		}

		System.out.println("Total number of capsules created: " + CoreJava2ndExercise.nCapsules);
	}
}
