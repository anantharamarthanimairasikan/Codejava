package com.prodapt.capstoneproject.entities;

public enum EResponse {
	PAID , IGNORED ,UNDELIVERABLE;

	  public static EResponse valueOf(int nextInt) {
	        EResponse[] values = EResponse.values();
	        return values[nextInt % values.length];

}
}
