package com.asiafrank.akka.fault.example1;

/**
 * WorkerApi
 * <p>
 * </p>
 * Created at 1/6/2017.
 *
 * @author zhangxf
 */
interface WorkerApi {
	Object Start = "Start";
	Object Do = "Do";

	class Progress {
		final double percent;

		public Progress(double percent) {
			this.percent = percent;
		}

		@Override
		public String toString() {
			return String.format("%s(%s)", getClass().getSimpleName(), percent);
		}
	}
}
