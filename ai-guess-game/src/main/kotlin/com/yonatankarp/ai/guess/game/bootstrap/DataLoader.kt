package com.yonatankarp.ai.guess.game.bootstrap

import com.yonatankarp.ai.guess.game.dto.RiddleDto
import com.yonatankarp.ai.guess.game.repository.RiddleRepository
import com.yonatankarp.ai.guess.game.utils.toHiddenString
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataLoader(
    private val riddleRepository: RiddleRepository
) : CommandLineRunner {

    companion object {
        private val log = LoggerFactory.getLogger(DataLoader::class.java)
    }

    override fun run(vararg args: String?) {
        loadData()
    }

    private fun loadData() =
        listOf(
            "man stands on a man" to "https://s3.amazonaws.com/ai.protogenes/art/28b9da08-4282-11ed-8be2-ee31c059bf00.png",
            "dolphin on fire" to "https://s3.amazonaws.com/ai.protogenes/art/840e15ae-18bb-11ed-9f15-ba15d03b6eca.png",
            "astronaut eating the moon" to "https://s3.amazonaws.com/ai.protogenes/art/9d73d604-4189-11ed-8cdd-2e988650e75d.png",
            "the quiet before the storm" to "https://s3.amazonaws.com/ai.protogenes/art/13bb8906-418a-11ed-8cdd-2e988650e75d.png",
            "dragon eating a cookie" to "https://s3.amazonaws.com/ai.protogenes/art/847263c4-42af-11ed-b2cd-366e053c1cb8.png",
            /**
             * The block bellow cannot be uncommented since:
             * 1. H2 doesn't support such a big URL
             * 2. Dall-e changes the image url every few hours
             * The only reason the below code is still here is for future reference and it should
             * be removed in the future
             */
//            "the flying spaghetti monster takes over the world" to "https://openailabsprodscus.blob.core.windows.net/private/user-K7p9uQj2dYynky205Mu1pisz/generations/generation-7PCRTIifZRZKcashcHNL6XQO/image.webp?st=2022-10-04T14%3A59%3A54Z&se=2022-10-04T16%3A57%3A54Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/webp&skoid=15f0b47b-a152-4599-9e98-9cb4a58269f8&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2022-10-04T14%3A40%3A35Z&ske=2022-10-11T14%3A40%3A35Z&sks=b&skv=2021-08-06&sig=ymN1/SDvu%2BIYFdirFLbQ%2B7a6dlx4Vv6%2BPUTx1MobfJw%3D",
//            "dog riding on a cookie to heaven" to "https://openailabsprodscus.blob.core.windows.net/private/user-K7p9uQj2dYynky205Mu1pisz/generations/generation-eoXJzojiGdz1lmzAGp5Z0ida/image.webp?st=2022-10-04T15%3A02%3A13Z&se=2022-10-04T17%3A00%3A13Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/webp&skoid=15f0b47b-a152-4599-9e98-9cb4a58269f8&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2022-10-04T15%3A30%3A22Z&ske=2022-10-11T15%3A30%3A22Z&sks=b&skv=2021-08-06&sig=oIQMz6PlLjGF/XS0w8dM5soHn7OuhaMJWw1j4xkIeDo%3D",
//            "agent fish flying above a forest of colorful mushrooms" to "https://openailabsprodscus.blob.core.windows.net/private/user-K7p9uQj2dYynky205Mu1pisz/generations/generation-oixgStYx4Dp7ee6PpZ2LrMd4/image.webp?st=2022-10-04T15%3A02%3A13Z&se=2022-10-04T17%3A00%3A13Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/webp&skoid=15f0b47b-a152-4599-9e98-9cb4a58269f8&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2022-10-04T15%3A30%3A22Z&ske=2022-10-11T15%3A30%3A22Z&sks=b&skv=2021-08-06&sig=d6CRslCVhi2%2BqsMvEMMgjDVhQqVFPQPH7JN3ppAiDiY%3D",
//            "whale that has dark starry skies in the black parts of it’s body" to "https://openailabsprodscus.blob.core.windows.net/private/user-K7p9uQj2dYynky205Mu1pisz/generations/generation-QWanAj2RQoNdUhqQRrDvIdoe/image.webp?st=2022-10-04T14%3A59%3A54Z&se=2022-10-04T16%3A57%3A54Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/webp&skoid=15f0b47b-a152-4599-9e98-9cb4a58269f8&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2022-10-04T14%3A40%3A35Z&ske=2022-10-11T14%3A40%3A35Z&sks=b&skv=2021-08-06&sig=Qj5omiXR8DjoeKHL2Pziy0bCJL9qEgQYpdaYewGiqok%3D",
//            "dolphins salute a captain" to "https://openailabsprodscus.blob.core.windows.net/private/user-K7p9uQj2dYynky205Mu1pisz/generations/generation-s4EMC72lNXGKBUFUNUXxgfHf/image.webp?st=2022-10-04T14%3A59%3A54Z&se=2022-10-04T16%3A57%3A54Z&sp=r&sv=2021-08-06&sr=b&rscd=inline&rsct=image/webp&skoid=15f0b47b-a152-4599-9e98-9cb4a58269f8&sktid=a48cca56-e6da-484e-a814-9c849652bcb3&skt=2022-10-04T14%3A40%3A35Z&ske=2022-10-11T14%3A40%3A35Z&sks=b&skv=2021-08-06&sig=r9iTADuGZiUSa4TmC9eqm/ppsEERmlo%2BD%2BX%2BdRJhTmI%3D"
        )
            .mapIndexed { index, pair ->
                riddleRepository.save(
                    RiddleDto(
                        id = index,
                        startPrompt = pair.first.toHiddenString(),
                        prompt = pair.first,
                        url = pair.second
                    )
                )
            }
            .filter { it }
            .also { riddles ->
                log.info("Loaded ${riddles.size} riddles to db...")
            }
}
