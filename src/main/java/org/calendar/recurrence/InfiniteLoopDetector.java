/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.calendar.recurrence;

/**
 * <b>Use to detect potential infinite loop</b>
 * <p>Will consider infinite loop if the detector is invoked over {@code thresholdOfLoop} of times within {@code thresholdOfTime} milliseconds.
 * <p>usage example</p>
 * <pre>
 * InfiniteLoopDetector detector = InfiniteLoopDetector.create(1000, 10000);
 * public void food() {
 *    detector.start();
 *    ...
 *    //detector.throwExcpetionIfDetected();
 *    if(detector.check()) {
 *        throw new InfiniteLoopException("Infinite loop detected!");
 *    }
 * }
 * </pre>
 * In above example, if the method {@code food} been called 10000 within 1 second, then it will throw exception;
 * @author <a href="mailto:raindy.ye@outlook.com">Raindy, Ye</a>
 *
 */
public class InfiniteLoopDetector {
	private long start;
	private int thresholdOfTime;//in millisecond units
	private int thresholdOfLoop = 100000;
	private int curValue;

	public InfiniteLoopDetector(int thresholdOfTime, int thresholdOfLoop) {
		super();
		this.thresholdOfTime = thresholdOfTime;
		this.thresholdOfLoop = thresholdOfLoop;
	}
	/**
	 * creates a infinite loop detector.
	 * @param thresholdOfTime in millisecond units
	 * @param thresholdOfLoop
	 * @return
	 */
	public static InfiniteLoopDetector create(int thresholdOfTime, int thresholdOfLoop) {
		return new InfiniteLoopDetector(thresholdOfTime, thresholdOfLoop);
	}

	public void start() {
		long elapsedTime = System.currentTimeMillis() - start;
		if (elapsedTime > thresholdOfTime) {
			start = System.currentTimeMillis();
			curValue = 0;
		}
		curValue++;
	}
 
	/**
	 * to check if potential infinite loop happen
	 * @return true if potential infinite loop happen
	 */
	public boolean check() {
		return curValue > thresholdOfLoop;
	}
	/**
	 * to check and throw exception if potential infinite loop happen
	 * @param message 
	 * @throws InfiniteLoopException
	 */
	public void throwExcpetionIfDetected() {
		throwExcpetionIfDetected(null);
	}
	
	/**
	 * to check and throw exception if potential infinite loop happen
	 * @param message error message of InfiniteLoopException when infinite loop happen detected
	 * @throws InfiniteLoopException
	 */
	public void throwExcpetionIfDetected(String message) {
		if (check()) {
			throw new InfiniteLoopException(message);
		}
	}
}
