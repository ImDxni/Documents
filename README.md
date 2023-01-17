# Documents
This plugin helps the management of the official documents of a player in a Roleplay Server, like ID Card (With player info as playtime and job) or health insurance card.

## Config

```
inventory: #inventories
  secretary: #secretary inventory
    title: "Computer segreteria"
    items:
      empty:
        '1':
          material: GRAY_STAINED_GLASS_PANE
          name: " "
          slot:
            row: 0
            column: 0
      identity:
        material: PLAYER_HEAD
        data: 2102
        name: "&bStampa carta d'identità"
        lore:
          - "&cClicca qui per stampare la carta d'identità!"
        slot:
          row: 2
          column: 2
      health:
        material: PLAYER_HEAD
        data: 2103
        name: "&bStampa tessera sanitaria"
        lore:
          - "&cClicca qui per stampare la tessera sanitaria!"
        slot:
          row: 2
          column: 2

  hospital: #hospital inventory
    title: "Computer Ospedale"
    items:
      empty:
        '1':
          material: GRAY_STAINED_GLASS_PANE
          name: " "
          slot:
            row: 0
            column: 0
      register:
        material: PLAYER_HEAD
        data: 2100
        name: "&bRegistra tessera sanitaria"
        lore:
          - "&cClicca qui per registrare la tessera sanitaria!"
        slot:
          row: 2
          column: 2
      check:
        material: PLAYER_HEAD
        data: 2101
        name: "&Scansiona tessera sanitaria"
        lore:
          - "&cClicca qui per scansionare la tessera sanitaria!"
        slot:
          row: 3
          column: 2
  select: #player select inventory
    title: "Scegli giocatore"

expires-in-days: 14 #expire time for cards

items:
  identity:
    material: STONE
    name: "Carta d'identità"
    lore:
      - "Proprietario: %player%"
      - "Scadenza: %expiration%"
  health:
    material: REDSTONE_BLOCK
    name: "Tessera Sanitaria"
    lore:
      - "Proprietario: %player%"
      - "Scadenza: %expiration%"


identity-book: #identity book, each section is as page
  '1':
    - "CARTA D'IDENTITA"
    - ""
    - ""
    - "LAVORO: %job%"
    - ""
    - "Tempo settimanale: %time%"

messages:
  wrong-type: "&cNon esiste un computer di questo tipo!"
  computer-added: "&2Computer registrato!"
  isnt-health-card: "&cDevi avere in mano la tessera sanitaria!"
  cant-break: "&cAccovacciati per rompere questo blocco!"
  computer-removed: "&2Compute rimosso!"
  card-printed: "Carta stampata!"
  card-registered: "La tessera sanitaria è registrata"
  card-not-registered: "La tessera sanitaria non è registrata!"
  card-registered-successfully: "Tessera sanitaria registrata correttamente!"
```

## Screenshots

![2023-01-17_15 22 48](https://user-images.githubusercontent.com/39953274/212929898-162c3c78-a6fc-4d86-b4ab-697ef3711102.png)
![2023-01-17_15 22 49](https://user-images.githubusercontent.com/39953274/212929914-546bf9fb-c27a-497d-a0dc-106078880754.png)
![2023-01-17_15 23 45](https://user-images.githubusercontent.com/39953274/212929954-1b69ca62-3ebe-4034-a166-c61c9131599d.png)
![2023-01-17_15 23 51](https://user-images.githubusercontent.com/39953274/212930010-9b2dcaee-f657-49aa-b86e-05a7d79555fb.png)
![2023-01-17_15 22 55](https://user-images.githubusercontent.com/39953274/212930043-6cc9f805-8a37-4f4a-b946-230c12a4db88.png)
![2023-01-17_15 22 56](https://user-images.githubusercontent.com/39953274/212930066-a7bf7990-6b7e-4892-a743-9adffd187716.png)
![2023-01-17_15 24 09](https://user-images.githubusercontent.com/39953274/212930122-122f7e43-6deb-481b-b3ae-e4a4e72e82ea.png)



