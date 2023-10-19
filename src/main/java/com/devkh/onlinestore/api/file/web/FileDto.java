package com.devkh.onlinestore.api.file.web;

import lombok.Builder;

@Builder
public record FileDto(String name,
                      String uri,
                      String downloadUri,
                      Long size,
                      String extension) {
}
