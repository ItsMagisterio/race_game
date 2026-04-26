# 3D Racing Game (Java + jMonkeyEngine)

Мини-игра в духе **Need for Speed Underground** на Java с твоими ассетами:
- `bmw_racing_car.glb` — машина
- `race_track.glb` — трасса
- `speedometer-car-tachometer.png` — HUD-спидометр

## Архитектура (не монолит)
Проект разбит на небольшие сервисы (microservice-style внутри приложения):
- `SceneService` — инициализация сцены, света, трека
- `CarService` — загрузка машины и логика движения
- `InputService` — только обработка управления
- `CameraService` — камера преследования
- `HudService` — UI/спидометр
- `CarState` — состояние машины

`RaceGameApp` теперь только оркестратор сервисов.

## Управление
- `W` / `↑` — газ
- `S` / `↓` — тормоз / задний ход
- `A` / `←` — поворот влево
- `D` / `→` — поворот вправо

## Запуск
```bash
mvn compile
mvn exec:java
```

## Ограничения
Это технический прототип в стиле NFS Underground, а не буквальная 1-в-1 копия AAA-игры.
