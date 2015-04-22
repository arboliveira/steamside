package br.com.arbo.steamside.collections;

import java.util.Optional;

import br.com.arbo.steamside.types.CollectionName;

public interface CollectionI {

	IsSystem isSystem();

	CollectionName name();

	enum IsSystem {
		NO, YES {

			@Override
			public Optional<String> toDTOString()
			{
				return Optional.of("Y");
			}
		};

		@SuppressWarnings("static-method")
		public Optional<String> toDTOString()
		{
			return Optional.empty();
		}

	}

}