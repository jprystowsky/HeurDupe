package io.mapping.apps.heurdupe.file;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ByteIntFileAbstractionReducer implements FileAbstractionReducer<byte[], Integer> {
	@Override
	public Integer reduceAbstraction(final byte[] abstraction) {
		return Arrays.hashCode(abstraction);
	}
}
