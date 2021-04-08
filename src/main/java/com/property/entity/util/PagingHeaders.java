package com.property.entity.util;

public enum PagingHeaders {
    PAGE_SIZE("Page-Size"),
    PAGE_NUMBER("Page-Number"),
    PAGE_OFFSET("Page-Offset"),
    PAGE_TOTAL("Page-Total"),
    COUNT("Count");

    private PagingHeaders(String name) {
		this.name = name;
	}

	private final String name;

	public String getName() {
		return name;
	}
}
