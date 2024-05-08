package org.carpet.utils.algebraic;

public interface AlgebraicDataModel<T> {
	int constructorCount();

	Class<T> modelledClass();

	AlgebraicDataModel<?>[][] constructors();

	T construct();
}
