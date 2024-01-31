import logging
import requests
import executor
from aiogram import Bot, Dispatcher, types

# Замените 'YOUR_BOT_TOKEN' на токен вашего бота
TOKEN = '6345275285:AAGinXEN4HsYegsj-g6bEvlwUT0oDrERGqY'
# Замените 'YOUR_SPOONACULAR_API_KEY' на ключ API Spoonacular
SPOONACULAR_API_KEY = '68065249552746bdbae5903805fc3c72'
# Словарь для хранения просмотренных рецептов пользователя
viewed_recipes = {}

# Настройка логирования
logging.basicConfig(format='%(asctime)s - %(name)s - %(levelname)s - %(message)s', level=logging.INFO)
logger = logging.getLogger(__name__)

bot = Bot(token=TOKEN)
dp = Dispatcher(bot=bot)  # Исправлено: передаем bot через именованный параметр

@dp.message_handler(commands=['start'])
async def start(message: types.Message) -> None:
    await message.answer('Привет! Я бот рецептов. Используйте /search для поиска рецептов.')

@dp.message_handler(commands=['search'])
async def search(message: types.Message) -> None:
    user_id = message.from_user.id
    keywords = message.get_args()

    if not keywords:
        await message.answer('Пожалуйста, укажите ключевые слова для поиска рецептов сразу после /search. Например /search pasta tomato cheese.')
        return

    # Преобразование списка ключевых слов в строку
    query = ' '.join(keywords)

    # Проверка, был ли рецепт просмотрен ранее
    if query in viewed_recipes.get(user_id, []):
        await message.answer('Этот рецепт уже был просмотрен.')
        return

    # Запрос к Spoonacular API для поиска рецептов
    url = f'https://api.spoonacular.com/recipes/search?query={query}&apiKey={SPOONACULAR_API_KEY}&number=5'
    response = requests.get(url)
    data = response.json()

    # Проверка успешности запроса
    if response.status_code == 200:
        recipes = data.get('results', [])

        if not recipes:
            await message.answer('Я таких не знаю. Попробуйте другие ключевые слова.')
            return

        # Отправка пользователю списка найденных рецептов
        for recipe in recipes:
            text = f"{recipe['title']}\nСсылка на рецепт: {recipe['sourceUrl']}"
            await message.answer(text, parse_mode=types.ParseMode.MARKDOWN)

        # Сохранение просмотренных рецептов пользователя
        viewed_recipes.setdefault(user_id, []).append(query)
    else:
        await message.answer('Ошибка при выполнении поиска. Пожалуйста, попробуйте позже.')

if __name__ == '__main__':
    # Запускаем бота
    from aiogram import executor
    executor.start_polling(dp, skip_updates=True)

