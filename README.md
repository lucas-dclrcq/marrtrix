<h1 align="center">
  Marrtrix
</h1>


<p align="center">
  <a href="https://github.com/lucas-dclrcq/marrtrix/actions"
    ><img
      src="https://github.com/lucas-dclrcq/marrtrix/actions/workflows/build.yml/badge.svg?branch=main"
      alt="GitHub Actions workflow status"
  /></a>
  <br />
  <a href="https://conventionalcommits.org"
    ><img
      src="https://img.shields.io/badge/Conventional%20Commits-1.0.0-yellow.svg"
      alt="Conventional commits"
  /></a>
  <a href="https://github.com/lucas-dclrcq/marrtrix/blob/main/LICENSE"
    ><img
      src="https://img.shields.io/github/license/lucas-dclrcq/marrtrix"
      alt="Repository license"
  /></a>
</p>

Sends notification from Sonarr and Radarr to a matrix room

## Install

1. Create a matrix user
2. Invite this user to the room where you want to send notifications
3. Start marrtrix :

```
docker -d run --restart=always \
-p 8080:8080 \
-e MATRIX_URL=https://matrix.org \
-e MATRIX_USERNAME=marrtrix \
-e MATRIX_PASSWORD=Marrtrix123 \
-e MATRIX_ROOM_ID="\!WCoiyAvDnhUEYbsItv:matrix.org" \
marrtrix:latest
```

4. Configure the webhook

TODO

## Configuration

You can configure Marrtrix using the following environment variables

| Name            | Required | Default | Description                                                                       | Example                      |
|-----------------|----------|---------|-----------------------------------------------------------------------------------|------------------------------|
| MATRIX_URL      | true     |         | The base url of the matrix server                                                 | https://matrix.org           |
| MATRIX_USERNAME | true     |         | A matrix username                                                                 | @user:matrix.org             |
| MATRIX_PASSWORD | true     |         | A matrix password                                                                 | password1234                 |
| MATRIX_ROOM_ID  | true     |         | The room internal id (in Element you'll find this id in Room settings > Advanced) | ie: !38748747DHDH:matrix.org |

## Roadmap

- [ ] Implement radarr notifications
- [ ] Implement sonarr notifications
- [ ] Implement lidarr notifications
- [ ] Implement readarr notifications
- [ ] Support E2E encryption