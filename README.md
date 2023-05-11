<h1 align="center">
  Marrtrix
</h1>


<p align="center">
  <a href="https://github.com/lucas-dclrcq/marrtrix/actions"
    ><img
      src="https://github.com/lucas-dclrcq/marrtrix/actions/workflows/development.yml/badge.svg?branch=main"
      alt="GitHub Actions workflow status"
  /></a>
  <a href="https://sonarcloud.io/summary/new_code?id=lucas-dclrcq_marrtrix"><img src="https://sonarcloud.io/api/project_badges/measure?project=lucas-dclrcq_marrtrix&metric=alert_status"></a>
  <a href="https://codecov.io/gh/lucas-dclrcq/marrtrix" > 
 <img src="https://codecov.io/gh/lucas-dclrcq/marrtrix/branch/main/graph/badge.svg?token=0TM5MJZC7O"/> 
 </a>
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
-e MARRTRIX_PASSWORD=AwesomePassword \
-e MARRTRIX_MATRIX_URL=https://matrix.org \
-e MARRTRIX_MATRIX_USERNAME=marrtrix \
-e MARRTRIX_MATRIX_PASSWORD=Marrtrix123 \
-e MARRTRIX_MATRIX_ROOM_ID="\!WCoiyAvDnhUEYbsItv:matrix.org" \
marrtrix:latest
```

4. Configure the webhook

TODO

## Configuration

You can configure Marrtrix using the following environment variables

| Name                     | Required | Default | Description                                                                       |
|--------------------------|----------|---------|-----------------------------------------------------------------------------------|
| MARRTRIX_DEBUG           | false    | false   | Enable debug mode where all incoming json requests are logged                     |
| MARRTRIX_PASSWORD        | true     |         | The password used to authenticate webhook requests using basic auth               |
| MARRTRIX_MATRIX_URL      | true     |         | The base url of the matrix server                                                 |
| MARRTRIX_MATRIX_USERNAME | true     |         | A matrix username                                                                 |
| MARRTRIX_MATRIX_PASSWORD | true     |         | A matrix password                                                                 |
| MARRTRIX_MATRIX_ROOM_ID  | true     |         | The room internal id (in Element you'll find this id in Room settings > Advanced) |

## Roadmap

- [ ] Implement radarr notifications
- [ ] Implement sonarr notifications
- [ ] Implement lidarr notifications
- [ ] Implement readarr notifications
- [ ] Support E2E encryption