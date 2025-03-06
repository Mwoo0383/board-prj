FROM nginx

# COPY  /usr/share/nginx/html

ENTRYPOINT [ "nginx", "-g", "daemon off;" ]