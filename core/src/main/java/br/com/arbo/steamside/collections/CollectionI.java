package br.com.arbo.steamside.collections;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.types.CollectionName;

public interface CollectionI {

	IsSystem isSystem();

	@NonNull
	CollectionName name();

	enum IsSystem {
		NO, YES;

		public static IsSystem fromDTOString(String dto)
		{
			return dto == null ? NO : YES;
		}

		public String toDTOString()
		{
			return this == NO ? null : "Y";
		}
	}

}