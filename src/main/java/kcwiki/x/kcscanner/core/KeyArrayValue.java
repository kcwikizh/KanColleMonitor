/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kcwiki.x.kcscanner.core;

/**
 *
 * @author iHaru
 */
public class KeyArrayValue {
    //舰娘语音加密数组
    public static int[] vcKey = {2475, 6547, 1471, 8691, 7847, 3595, 1767, 3311, 2507, 9651, 5321, 4473, 7117, 5947, 9489, 2669, 8741, 6149, 1301, 7297, 2975, 6413, 8391, 9705, 2243, 2091, 4231, 3107, 9499, 4205, 6013, 3393, 6401, 6985, 3683, 9447, 3287, 5181, 7587, 9353, 2135, 4947, 5405, 5223, 9457, 5767, 9265, 8191, 3927, 3061, 2805, 3273, 7331};
    //推测为母港bgm加密数组
    public static int[] resource = {6657, 5699, 3371, 8909, 7719, 6229, 5449, 8561, 2987, 5501, 3127, 9319, 4365, 9811, 9927, 2423, 3439, 1865, 5925, 4409, 5509, 1517, 9695, 9255, 5325, 3691, 5519, 6949, 5607, 9539, 4133, 7795, 5465, 2659, 6381, 6875, 4019, 9195, 5645, 2887, 1213, 1815, 8671, 3015, 3147, 2991, 7977, 7045, 1619, 7909, 4451, 6573, 4545, 8251, 5983, 2849, 7249, 7449, 9477, 5963, 2711, 9019, 7375, 2201, 5631, 4893, 7653, 3719, 8819, 5839, 1853, 9843, 9119, 7023, 5681, 2345, 9873, 6349, 9315, 3795, 9737, 4633, 4173, 7549, 7171, 6147, 4723, 5039, 2723, 7815, 6201, 5999, 5339, 4431, 2911, 4435, 3611, 4423, 9517, 3243};
    public static int[] RND = {3367,28012,6269,26478,24442,27255,28017,3366,6779,7677,7179,28011,24421,27502,3366,7779,24439,27762,6474,7463,28515,5164,6672,28006,27999,27254,7363,6868,13420,6464,7376,28268,6968,7276,29806,28778,27511,7163,25964,7870,29549,31084,7063,7163,7969,6674,7934,6774,6768,27769,7476,7272,28279,30572,6763,27759,7768,27768,7864,28535,27235,7968,7373,27769,27751,3479,27497,6769,28272,7264,7773,6878,6178,7673,6671,6673,26476,6367,7162,28021,7773,6573,27251,27188,7778,27255,27750,7268,6763,27500,6664,7369,25199,31353,25455,7875,7369,7464,7262,6376,7370,27245,3474,7469,28535,28536,6276,30827,31349,7071,30059,26991,6771,6266,6572,7570,6469,27752,28258,7573,7334,6666,6573,27768,7575,28261,25199,6366,6268,7975,7268,7176,27509,6164,7662,6470,6368,7075,30062,3461,27234,26479,7977,6962,28015,27747,27766,28270,28017,27495,28016,6379,28015,29550,25964,27755,28522,7635,31329,26475,7473,6169,6478,27999,6161,27491,6765,7579,6468,26463,31336,6562,7564,6773,3564,7473,7564,30570,6274,6363,6663,7670,7173,27249,7278,28259,7974,6670,7578,6463,7835,26207,25466,6173,27495,6664,6268,7272,24441,29039,26734,6262,7979,24938,6768,27493,7574,3579,6673,27508,28274,6261,26988,6463,6362,6166,26475,6663,27241,7876,28270,6475,6766,31286,28278,28270,28520,27498,31086,7077,27242,6966,26219,7069,28514,27760,6971,27256,7363,27244,3677,6669,6270,7362,6879,6971,27503,6778,7879,7376,6375,27756,6965,7163,7367,6579,7236,7873,30829,6975,6367,28521,26733,7972,30317,6977,6376,27501,27239,30063,28262,6870,6773,3669,6361,7973,27769,6777,28518,27757,27252,6677,27242,6471,6566,27235,31346,7270,6364,6536,7566,6471,6371,28025,29039,30315,29546,27495,6763,7576,6979,31087,27760,7167,7673,6662,3770,6477,6369,25966,6472,31333,7378,27240,6968,7273,28277,7769,6863,27002,7573,7279,6337,30059,28002,6968,6568,28269,6578,28263,7570,26987,7865,6478,7870,29562,24942,7872,6967,3779,25962,6467,29549,7365,7378,7272,6273,28260,7361,6573,28005,7772,30571,25978,6667,27448,24954,30842,28008,6765,31098,25966,25198,6663,7766,6169,7772,28009,6477,6177,30061,26221,3867,6277,28265,6174,27234,6677,27489,6163,30842,6675,24942,26234,30827,6172,6277,7964,6238,6778,30574,7572,6274,27493,29802,27234,7974,7069,6767,6470,28013,26475,6362,7275,28272,14443,6566,6477,29290,31082,6472,6668,6363,6467,6772,6378,28781,28270,6467,7065,7962,6338,26746,31352,6364,25210,6172,29291,27245,6264,6267,7267,6870,27492,31335,6378,6766,28266,3962,7562,31338,6479,7875,7874,6562,26730,6770,28267,6677,7073,6870,6565,26477,7462,6239,6561,27491,6968,25709,28272,7561,7270,6774,6767,7067,6679,6874,31335,7377,27235,6769,14701,7470,25451,31354,25451,28270,6568,7574,27513,25451,6473,27496,7074,6875,6866,7367,27449,28279,6163,31098,7264,26475,30586,31341,6579,6767,7474,7365,6877,7468,27508,25978,31349,3962,24939,7961,6162,27507,7279,6861,7077,7974,7563,28021,26730,6574,28259,6570,28003,27961,7265,7879,27241,28257,7574,6179,7277,6964,26477,30062,6362,7574,6478,6366,6974,6367,3974,7370,31336,7373,28008,27238,7472,6767,28010,31085,6461,7269,29562,28282,6565,7764,27473,6466,29293,26221,24426,7673,6368,6800};
    
    
}
