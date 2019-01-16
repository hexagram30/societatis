# hexagram30/societatis

*Society generation and evolution for use by hexagram30 projects*

[![][logo]][logo-large]


## About

From the perspective of an interactive, story-driven, world-oriented
adventure roll-playing game, the presence of a society is of critical
importance; ideally, two or more interacting societies are present. As a
story or game element, societies provide the following:

* a sensible context for characters, both playing and non-playing
* global, regional, municipal, and tribal backstory
* diversity of quests
* source of ample new supporting content for stories

A particular use case that motivated the creation of this project was
in-game news feeds. I grow weary of all the real-life bad news we see so
much of these days. I would love to open up a news reader in the morning
which drinking my coffee, and see what has been going on in a given
generated game world: a queen's new diplomatic achievement, an arcane
college discovering a new planar entity, the creation of a new village,
the founding of a new farm, the birth of twins to a tribe's leading
family, etc. Elements that would not only make for interesting or fun
news items to read about, but which would then be incorporated into
actual game-play, when I eventually logged in later in the day or week.

First things first, though: a society needs to be created! So, to
achieve the above desired usage, this project starts at the beginning
...


## Methodology

The societatis (pronounced so-ki-eh-TA-tis) project provides code that
evolves an eventual collection of societies from one or more seed
populations at one or more geographic locations. As such, there are two
core parts to the low-level API:

* population growth
* population migration


### Population Growth

See https://en.wikipedia.org/wiki/Population_growth#Population_growth_rate
and https://en.wikipedia.org/wiki/Logistic_function#In_ecology:_modeling_population_growth

Carrying Capacity

See https://en.wikipedia.org/wiki/Carrying_capacity#Factors_that_govern_carrying_capacity

Growth Rate

Births

Deaths

Death types

* perinatal mortality
* maternal mortality
* child mortality (less than five years old)
  * Preterm birth complications (18%)
  * Pneumonia (16%)
  * Interpartum-related events (12%)
  * Neonatal sepsis (7%)
  * Diarrhea (8%)
  * Malaria (5%)
  * Malnutrition and Under nutrition
* age mortality
  * Cardiovascular diseases (34%)
  * Infectious and parasitic diseases (26%)
  * Cancers (14%)
  * Respiratory diseases (7%)
  * Unintentional injuries (7%)
  * Digestive diseases (3%)
  * Intentional injuries (suicide, violence, war, etc.) (3%)
  * Neuropsychiatric disorders (2%)
  * Diabetes mellitus (2%)
  * Diseases of the genitourinary system (1%)
  * Nutritional deficiencies (1%)

Rough guesses at percentage of loss in high-risk societies:

* perinatal mortality: 10-20%
* maternal mortality: 2%
* child mortality: 10%
* age mortality:
  * see https://en.wikipedia.org/wiki/Gompertz%E2%80%93Makeham_law_of_mortality

Starting population:

See https://worldbuilding.stackexchange.com/questions/3/what-is-the-minimum-human-population-necessary-for-a-sustainable-colony


### Population Migration

Since 1990, 3% of the world's population immigrates every year. Another
8% would like to. Let's assume that:

* migrations only start with a group of a size roughly the Dunbar number
* that an initial migration will only occur if
  * the population grows so large that 2-12% of it is roughly the Dunbar number in
    size
  * a significant event occurs (e.g., fire, flood, disease, famine) that drives a
    greater percentage of the population into migration
  * a catastrophe occurs that drives 100% of the (surviving) population away from
    their home
  * once a migration starts, the presence of new resources and a doubling or
    tripling of the population causes the migration to bifurcate (or trifurcate)
  * that a settlement occurs only when a number of resource requirements are met,
    at which point the cycle will have to start over again (waiting for the population to support a Dunbar-sized group that is 2-12% of the settlement's
    population)

Resource evaluation during migration:

* presence of river, ocean, valuable natural resource


## Donating

A donation account for supporting development on this project has been
set up on Liberapay here:

* [https://liberapay.com/hexagram30/donate](https://liberapay.com/hexagram30/donate)

You can learn more about Liberapay on its [Wikipedia entry][libera-wiki]
or on the service's ["About" page][libera-about].

[libera-wiki]: https://en.wikipedia.org/wiki/Liberapay
[libera-about]: https://liberapay.com/about/


## License

```
Copyright © 2018-2019, Hexagram30 <hexagram30@cnbb.games>

Apache License, Version 2.0
```

<!-- Named page links below: /-->

[logo]: https://raw.githubusercontent.com/hexagram30/resources/master/branding/logo/h30-logo-2-long-with-text-x695.png
[logo-large]: https://raw.githubusercontent.com/hexagram30/resources/master/branding/logo/h30-logo-2-long-with-text-x3440.png
[comp-event]: https://github.com/hexagram30/hexagramMUSH/blob/master/src/hexagram30/mush/components/event.clj
