package com.order.tag;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		int[] a = new int[4];
		ss();
	}

	public static void method(StringBuffer b, String c) {
		ss();

	}

	public static void ss() {
		try {
			mm();

			System.out.print("a");
		} catch (RuntimeException e) {
			System.out.print("b");
			throw e;
		} finally {
			System.out.print("c");
		}
		System.out.print("d");
	}

	public static void mm() {
		throw new RuntimeException();
	}

}
