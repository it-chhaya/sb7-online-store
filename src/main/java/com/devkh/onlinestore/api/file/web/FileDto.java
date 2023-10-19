package com.devkh.onlinestore.api.file.web;

import lombok.Builder;

@Builder
public record FileDto(String name,
                      String uri,
                      Long size,
                      String extension) {
}
