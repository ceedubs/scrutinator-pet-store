package net.ceedubs.scrutinator.petstore

trait PetDao {
  def save(pet: Pet): Unit

  def byId(id: Int): Option[Pet]

  def deleteById(id: Int): Unit

  // Some(()) if the pet existed and was updated. Otherwise None
  def update(pet: Pet): Option[Unit]

  def byStatus(statuses: Set[String]): List[Pet]
}

class MapPetDao(initialPets: Map[Int, Pet]) extends PetDao {
  import collection.JavaConverters._

  private[this] val pets: collection.concurrent.Map[Int, Pet] =
    new java.util.concurrent.ConcurrentHashMap[Int, Pet](initialPets.asJava).asScala

  def save(pet: Pet) = pets.put(pet.id, pet)

  def byId(id: Int) = pets.get(id)

  def deleteById(id: Int) = pets.remove(id)

  def update(pet: Pet): Option[Unit] = pets.replace(pet.id, pet).map(_ => ())

  def byStatus(statuses: Set[String]): List[Pet] =
    pets.values.filter(_.status.exists(statuses.contains)).toList

}

object MapPetDao {
  val dummyInstance: PetDao = {
    val pets = Map(
      1 -> Pet(
        id = 1,
        category = Some(Category(
          id = Some(11),
          name = Some("category 11"))),
        name = "fido",
        photoUrls = None,
        tags = Some(List(
          Tag(
            id = Some(21),
            name = Some("tag 21")),
          Tag(
            id = Some(22),
            name = Some("tag 22")))),
        status = Some("available")))

    new MapPetDao(pets)
  }
}
