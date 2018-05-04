package com.njwb.transaction;

public interface Transaction {
	public abstract void begin();
	public abstract void commit();
	public abstract void rollBack();
}
