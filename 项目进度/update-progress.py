import os
import re


class UpdateProgress:
    __directory = '../计算机/项目'
    __target_file = './项目总览.md'

    def scan_folder(self):
        print('项目总览')
        result = ['| 项目名称  | 进度 | 未完成项 |\r', '| --------------------- | ----- | ----- |\r']
        for root, dirs, files in os.walk(self.__directory):
            if len(files) > 0:
                for each_file in files:
                    if each_file.endswith('md'):
                        with open(f'{root}/{each_file}', 'r', encoding='UTF-8') as file:
                            count_ok = 0
                            count_no = 0
                            found = False
                            not_ok_titles = []
                            for each_line in file:
                                if found or '### 功能列表' in each_line:
                                    found = True
                                    if each_line.startswith('- [x]'):
                                        count_ok += 1
                                    elif each_line.startswith('- [ ]'):
                                        count_no += 1
                                        not_ok_titles.append(re.sub('(\- \[ \])|\n', '', each_line))
                            if found:
                                filename = each_file.split(".md")[0]
                                percent = count_ok * 100 / (count_ok + count_no)
                                tips = ",".join(not_ok_titles)
                                result.append(f'| [[{filename}]] | {percent:.2f}% |{tips}|\r')
                                print(f'{filename.ljust(22)}', end='')
                                print(f'进度{percent:.2f}%', end='')
                                if percent == 100:
                                    print('\t祝贺')
                                else:
                                    print(f'\t未完成项:{tips}')
        with open(self.__target_file, 'w', encoding='UTF-8') as file:
            file.writelines(result)


UpdateProgress().scan_folder()
input('按任意键退出')
