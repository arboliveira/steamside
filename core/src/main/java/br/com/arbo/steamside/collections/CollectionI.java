package br.com.arbo.steamside.collections;

import java.util.stream.Stream;

import br.com.arbo.steamside.types.CollectionName;

public interface CollectionI {

	Stream<Tag> apps();

	IsSystem isSystem();

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