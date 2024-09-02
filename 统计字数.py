import os
import re


class CharacterStatistic:

    def __init__(self):
        self.__directory = './'
        self.__character_count = 0

    def scan_folder(self):
        print('开始统计')
        for root, dirs, files in os.walk(self.__directory):
            if len(files) > 0:
                for each_file in files:
                    if each_file.endswith('md'):
                        with open(f'{root}/{each_file}', 'r', encoding='UTF-8') as file:
                            content = file.read()
                            self.__character_count = self.__character_count + len(content)
        print(f'总字数:{self.__character_count}')


CharacterStatistic().scan_folder()
input('按任意键退出')
