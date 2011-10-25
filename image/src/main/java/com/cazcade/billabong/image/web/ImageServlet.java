package com.cazcade.billabong.image.web;

import com.cazcade.billabong.image.CacheResponse;
import com.cazcade.billabong.image.ImageService;
import com.cazcade.billabong.image.ImageSize;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class ImageServlet {

    private ImageService imageService;

    public ImageServlet(ImageService imageService) {
        this.imageService = imageService;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String url = request.getParameter("url");
        final ImageSize imageSize = ImageSize.valueOf(request.getParameter("imageSize"));
        try {
            CacheResponse cacheResponse = imageService.getCacheURIForImage(new URI(url), imageSize, true);
            //TODO return response in format acceptable to client.
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
