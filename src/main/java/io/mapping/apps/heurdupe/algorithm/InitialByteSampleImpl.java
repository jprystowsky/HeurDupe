/*
 * Copyright 2013 Jacob Miles Prystowsky
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.mapping.apps.heurdupe.algorithm;

import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * A concrete implementation of {@link InitialByteSample} utilizing a byte array.
 */

@Component
public class InitialByteSampleImpl implements InitialByteSample, Comparable<InitialByteSample> {
	private byte[] bytes;

	@Override
	public byte[] getBytes() {
		return this.bytes;
	}

	@Override
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		} else if (obj instanceof InitialByteSampleImpl) {
			return compareTo((InitialByteSampleImpl) obj) == 0;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(InitialByteSample o) {
		return Arrays.hashCode(this.bytes) - Arrays.hashCode(o.getBytes());
	}

	@Override
	public byte[] getAbstraction() {
		return this.bytes;
	}
}
