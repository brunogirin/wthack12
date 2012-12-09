'''
Created on 9 Dec 2012

@author: bruno
'''
class BarChart:
    def __init__(self, width, height, data):
        self.width = width
        self.height = height
        self.series_hspace = width / len(data)
        self.series_width = self.series_hspace * 0.8
        self.series_pad = self.series_hspace * 0.1
        self.bar_hspace = self.series_width / max([len(s[1]) for s in data])
        self.bar_width = self.bar_hspace * 0.8
        self.bar_pad = self.bar_hspace * 0.1
        self.vmax = max([max([v[1] for v in s[1]]) for s in data])
        self.bar_ratio = 0.8 * height / self.vmax
        self.legends = [{
                         'legend': v,
                         'x': self.series_hspace * (i + 0.5),
                         'y': height * 0.95
                         } for i, v in enumerate([d[0] for d in data])]
        self.colours = ["red", "green", "blue", "yellow"]
        self.bars = [
                     [{
                       'x': i * self.series_hspace + self.series_pad + j * self.bar_hspace + self.bar_pad,
                       'y': self.height * 0.9 - v[1] * self.bar_ratio,
                       'width': self.bar_width,
                       'height': v[1] * self.bar_ratio,
                       'colour': self.colours[j],
                       'labelx': i * self.series_hspace + self.series_pad + (j + 0.5) * self.bar_hspace,
                       'labely': self.height * 0.85 - v[1] * self.bar_ratio,
                       'label': str(v[1])
                       } for j, v in enumerate(s[1])]
                     for i, s in enumerate(data)
                     ]
        self.floorline = {
                          'x1': 0,
                          'y1': height * 0.9,
                          'x2': width,
                          'y2': height * 0.9
                          }    
