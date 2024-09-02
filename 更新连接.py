import os
import re


def get_reg(str_input):
    str_input = str_input.replace('!', r'\!')
    str_input = str_input.replace('=', r'\=')
    str_input = str_input.replace('+', r'\+')
    str_input = str_input.replace('.', r'\.')
    str_input = str_input.replace('*', r'\*')
    str_input = str_input.replace('?', r'\?')
    str_input = str_input.replace('(', r'\(')
    str_input = str_input.replace(')', r'\)')
    str_input = str_input.replace('|', r'\|')
    str_input = str_input.replace('$', r'\$')
    str_input = str_input.replace('^', r'\^')
    str_input = str_input.replace('\\', r'\\\\')
    str_input = str_input.replace('{', r'\{')
    str_input = str_input.replace('}', r'\}')

    return fr'{str_input}(?![^\[]*\])'


class UpdateLink:
    __directory = './'
    __file_count = 0
    __file_list_md = []
    __file_processed_count = 0
    __link_updated_count = 0

    def scan_folder(self):
        # os.walk会递归遍历
        for root, dirs, files in os.walk(self.__directory):
            if len(files) > 0:
                for each_file in files:
                    if each_file.endswith('md'):
                        self.__file_list_md.append(each_file)
                self.__file_count += len(files)
        print(f'文件总数：{self.__file_count}')
        print(f'md文件数：{len(self.__file_list_md)} 占({(len(self.__file_list_md) / self.__file_count * 100):.2f}%)')
        for root, dirs, files in os.walk(self.__directory):
            if len(files) > 0:
                for each_file in files:
                    if each_file.endswith('md'):
                        line_buffer = []
                        with open(f'{root}/{each_file}', 'r', encoding='UTF-8') as file:
                            for each_line in file:
                                line_buffer.append(each_line)
                        with open(f'{root}/{each_file}', 'w', encoding='UTF-8') as file:
                            number_of_quote = 0
                            for index, each_line in enumerate(line_buffer):
                                for each_filename in self.__file_list_md:
                                    if each_filename == each_file:
                                        continue
                                    # 文件名在行中并且两端没有双中括号的
                                    if number_of_quote % 2 == 0:
                                        filename_without_suffix = each_filename.split('.md')[0]
                                        split_line = line_buffer[index].split(filename_without_suffix)
                                        merged_result = []
                                        # 对于行中出现了文件名，并且文件名没有在超链接里的（这样做会导致超链接以外的本行内容即使含有文件名也不会更新）
                                        if len(split_line) > 1 and not re.search(rf'[a-z]+://.*{filename_without_suffix}.*',each_line):
                                            number_of_quote_line = 0
                                            number_of_brackets_line_left = 0
                                            number_of_brackets_line_right = 0
                                            for index_s, item in enumerate(split_line):
                                                if index_s == len(split_line) - 1:
                                                    merged_result.append(split_line[index_s])
                                                    continue
                                                number_of_quote_line += split_line[index_s].count('`')
                                                number_of_brackets_line_left += split_line[index_s].count('[')
                                                number_of_brackets_line_right += split_line[index_s].count(']')
                                                merged_result.append(split_line[index_s])
                                                if number_of_quote_line % 2 == 0 and number_of_brackets_line_left == number_of_brackets_line_right:
                                                    merged_result.append(f'[[{filename_without_suffix}]]')
                                                    self.__link_updated_count = self.__link_updated_count + 1
                                                else:
                                                    merged_result.append(filename_without_suffix)
                                        else:
                                            merged_result.append(line_buffer[index])
                                        line_buffer[index] = ''.join(merged_result)
                                # 数`的个数
                                number_of_quote += each_line.count('`')
                            file.writelines(line_buffer)

        print(f'已更新链接：{self.__link_updated_count}')


UpdateLink().scan_folder()
input('按任意键退出')
