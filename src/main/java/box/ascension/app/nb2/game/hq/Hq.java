package box.ascension.app.nb2.game.hq;

import box.ascension.app.nb2.game.hq.buildings.EGen;

@SuppressWarnings("unchecked")
public class Hq {

	public static enum Building {
		GENERATOR,
		RESEARCH,
		FACTORY,
		MAINTENANCE
	}
	
	public final ProgTree<EGen> GENERATOR = 
		ProgTree.of(EGen.PETROL).and(
			ProgTree.of(EGen.PETROL_II).and(
				ProgTree.of(EGen.PETROL_III).and(
					ProgTree.of(EGen.PETROL_IV).and(
						ProgTree.of(EGen.PETROL_V).and(
							ProgTree.of(EGen.PETROL_VI).and(
								ProgTree.of(EGen.PETROL_VII).and(
									ProgTree.of(EGen.PETROL_VIII).and(
										ProgTree.of(EGen.PETROL_IX).and(
											ProgTree.of(EGen.PETROL_X))))))),
						ProgTree.of(EGen.FISSION).and(
							ProgTree.of(EGen.FISSION_II).and(
								ProgTree.of(EGen.FISSION_III).and(
									ProgTree.of(EGen.FISSION_IV).and(
										ProgTree.of(EGen.FISSION_V).and(
											ProgTree.of(EGen.FISSION_VI).and(
												ProgTree.of(EGen.FISSION_VII).and(
													ProgTree.of(EGen.FISSION_VIII).and(
														ProgTree.of(EGen.FISSION_IX).and(
															ProgTree.of(EGen.FISSION_X)))))),
										ProgTree.of(EGen.FUSION).and(
											ProgTree.of(EGen.FUSION_II).and(
												ProgTree.of(EGen.FUSION_III).and(
													ProgTree.of(EGen.FUSION_IV).and(
														ProgTree.of(EGen.FUSION_V).and(
															ProgTree.of(EGen.FUSION_VI).and(
																ProgTree.of(EGen.FUSION_VII).and(
																	ProgTree.of(EGen.FUSION_VIII).and(
																		ProgTree.of(EGen.FUSION_IX).and(
																			ProgTree.of(EGen.FUSION_X))))),
																ProgTree.of(EGen.RED_FUSION_II).and(
																	ProgTree.of(EGen.RED_FUSION_III).and(
																		ProgTree.of(EGen.RED_FUSION_IV).and(
																			ProgTree.of(EGen.RED_FUSION_V).and(
																				ProgTree.of(EGen.RED_FUSION_VI).and(
																					ProgTree.of(EGen.RED_FUSION_VII).and(
																						ProgTree.of(EGen.RED_FUSION_VIII).and(
																							ProgTree.of(EGen.RED_FUSION_IX).and(
																								ProgTree.of(EGen.RED_FUSION_X)))))))))))))))))))));
	public final ProgTree<EGen> FACTORY = new ProgTree<>(); 	
	public final ProgTree<EGen> RESEARCH = new ProgTree<>(); 	
	public final ProgTree<EGen> MAINTENANCE = new ProgTree<>(); 		
	
	public Hq() {

	}

	public boolean upgrade(Building building, int[] indices) {
		ProgTree<?> progTree = switch (building) {
			case FACTORY -> FACTORY;
			case GENERATOR -> GENERATOR;
			case MAINTENANCE -> MAINTENANCE;
			case RESEARCH -> RESEARCH;
			default -> {
				System.out.println("Tried to upgrade an invalid building");
				yield null;
			}
		};
		if (progTree != null && progTree.isUnlocked(indices)) {
			progTree.get(indices).unlock();
			return true;
		} else {
			return false;
		}
	}

}
