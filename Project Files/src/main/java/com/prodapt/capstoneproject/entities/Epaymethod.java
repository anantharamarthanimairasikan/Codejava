package com.prodapt.capstoneproject.entities;

public enum Epaymethod {
	CREDIT_CARD,BANK_TRANSFER,UPI;
	
	public static Epaymethod valueOf(int nextInt) {
		Epaymethod[] values = Epaymethod.values();
        return values[nextInt % values.length];

}
}
